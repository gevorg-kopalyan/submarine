package com.submarine.applovin;

import android.app.Activity;
import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.sdk.*;

import java.util.Map;

/**
 * Created by Gev on 4/6/2016.
 */

public class AppLovinNetwork implements AppLovinNetworkCore{
    private final AppLovinIncentivizedInterstitial myIncent;
    Activity activity;
    AppLovinRewardVideoListener revardedVideoListener;
    AppLovinAdRewardListener adRewardListener = new AppLovinAdRewardListener() {


        @Override
        public void userRewardVerified(AppLovinAd appLovinAd, Map map) {
            //if(revardedVideoListener != null) //TODO
                //revardedVideoListener.userRewardVerified();
        }

        @Override
        public void userOverQuota(AppLovinAd appLovinAd, Map map) {

        }

        @Override
        public void userRewardRejected(AppLovinAd appLovinAd, Map map) {

        }

        @Override
        public void validationRequestFailed(AppLovinAd appLovinAd, int i) {

        }

        @Override
        public void userDeclinedToViewAd(AppLovinAd appLovinAd) {

        }
    };

    public AppLovinNetwork(Activity activity) {
        this.activity = activity;

        // initialize Sdk.
        AppLovinSdk.initializeSdk(activity);

        // Create a rewarded video.
        myIncent = AppLovinIncentivizedInterstitial.create(activity);

        // Preload call not using a load listener.
        myIncent.preload(null);

    }

    @Override
    public void showRewardedVideo(){
        // Check to see if an ad is ready before attempting to show.
        if(myIncent.isAdReadyToDisplay()){
            // An ad is ready.  Display the ad with one of the available show methods.
            myIncent.show(activity);
        }
        else{
            // No rewarded ad is currently available.
        }
    }

    @Override
    public boolean isVideoReadyToDisplay() {
        return myIncent.isAdReadyToDisplay();
    }

    @Override
    public void preloadVideo(){
        // Preload call using a new load listener
        myIncent.preload(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd) {
                // A rewarded video was successfully received.
            }
            @Override
            public void failedToReceiveAd(int errorCode) {
                // A rewarded video failed to load.
            }
        });
    }

    public void onDestroy(){
        myIncent.dismiss();
    }
}
