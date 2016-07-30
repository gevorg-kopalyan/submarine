package com.submarine.playgameservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class AndroidGPGSBase implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "AndroidGPGSBase";
    private final FragmentActivity activity;
    private GoogleApiClient mGoogleApiClient;
    private String idToken = "";
    private static final int RC_SIGN_IN = 9001;
    private String clientID;
    private boolean signInFlowInitiated;
    private boolean mResolvingConnectionFailure;

    public AndroidGPGSBase(FragmentActivity activity ) {
        this.activity = activity;
    }

    /**
     * Sign in to google play games services for getting ServerAuthCode
     * <p>
     * This method  Signs in player with getting ServerAuthCode for authentication
     * in back-end server.
     * @param  clientID  server client ID
     */
    public void signIn(String clientID){
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                //.addApi(Games.API,gso);
                .build();
        mGoogleApiClient.connect();

        this.clientID = clientID;
        signInFlowInitiated = true;
    }

    /**
     * Sign in to google play games services
     * <p>
     * This method  Signs in player
     */
    public void signIn(){
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                //.addApi(Games.API,gso);
                .build();
        mGoogleApiClient.connect();
        signInFlowInitiated = true;
    }

    public void signOut(){
        Games.signOut(mGoogleApiClient);
        mGoogleApiClient.disconnect();
        signInFlowInitiated = false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //TODO connection success notify external listeners
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended() called. Trying to reconnect.");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (signInFlowInitiated) {

            if (mResolvingConnectionFailure) {
                Log.d(TAG, "onConnectionFailed() ignoring connection failure; already resolving.");
                return;
            }

            if(!signInFlowInitiated){
                Log.d(TAG, "hmm WTF WHY :D");
                return;
            }
            signInFlowInitiated = false;

            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(activity, RC_SIGN_IN);
                    mResolvingConnectionFailure = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mGoogleApiClient.connect();
                    mResolvingConnectionFailure = false;
                }
            } else {
                // not resolvable... so show an error message
                int errorCode = connectionResult.getErrorCode();
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, errorCode, RC_SIGN_IN);
                if (dialog != null) {
                    dialog.show();
                } else {
                    // no built-in dialog: show the fallback error message
                    GPGSUtils.showAlert(activity, "SIGN IN ERROR");
                }
                mResolvingConnectionFailure = false;
            }
        }



        Log.d(TAG,"onConnectionFailed");
        Log.d(TAG,"   - code: " + connectionResult.getErrorCode());
        Log.d(TAG,"   - errorMessage: " + connectionResult.getErrorMessage());
        Log.d(TAG,"   - resolvable: " + connectionResult.hasResolution());
        Log.d(TAG,"   - details: " + connectionResult.toString());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

        }
    }

    public String getIdToken(){
        return idToken;
    }

    private class SendTokenTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String ... asd) {

            Games.GetServerAuthCodeResult result  = Games.getGamesServerAuthCode(mGoogleApiClient, clientID).await();
            System.out.println("ServerAuthCode: " + result.getCode());
            return result.getCode();
        }

    }
}
