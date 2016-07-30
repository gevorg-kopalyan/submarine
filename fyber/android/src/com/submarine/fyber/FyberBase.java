/**
 * Fyber Android SDK
 * <p/>
 * Copyright (c) 2015 Fyber. All rights reserved.
 */
package com.submarine.fyber;

import android.app.Activity;
import android.content.Intent;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.fyber.utils.FyberLogger;

public abstract class FyberBase implements RequestCallback, FyberBaseCore {

	protected static final int INTERSTITIAL_REQUEST_CODE = 8792;
	protected static final int REWARDED_VIDEO_REQUEST_CODE = 8796;
    protected final Activity activity;

    private boolean isRequestingState;
	protected Intent intent;
    private  AdRequestCallback adListener = null;

	public FyberBase(Activity activity) {
		this.activity = activity;
	}

	//@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//resetting button and Intent

		resetIntent();
	}

	/*
	* ** Fragment specific methods **
	*/

	protected abstract String getLogTag();

	protected abstract int getRequestCode();

	protected abstract void performRequest();

    @Override
	public void requestAd() {
		//avoid requesting an ad when already requesting
		if (!isRequestingState()) {
			setToRequestingMode();
			//perform the ad request. Each Fragment has its own implementation.
			performRequest();

		}
	}

    @Override
    public void showAd() {
		//if we already have an Intent, we start the ad Activity
		if (isIntentAvailable()) {
			//start the ad format specific Activity
			activity.startActivityForResult(intent,getRequestCode());

		}
	}

	/*// when a button is clicked, request or show the ad according to Intent availability
	protected void requestOrShowAd() {
		//avoid requesting an ad when already requesting
		if (!isRequestingState()) {
			//if we already have an Intent, we start the ad Activity
			if (isIntentAvailable()) {
				//start the ad format specific Activity
				activity.startActivityForResult(intent,getRequestCode());

			} else {
				setToRequestingMode();
				//perform the ad request. Each Fragment has its own implementation.
				performRequest();
			}
		}
	}*/

	/*
	* ** RequestCallback methods **
	*/

	@Override
	public void onAdAvailable(Intent intent) {
		resetRequestingState();
		this.intent = intent;
		//if you are using a general purpose requestCallback like this you might want to verify which adFormat will this Intent show.
		//You can use the AdFormat class to obtain an AdFormat from a given Intent. Then you can perform ad format specific actions e.g.:
		AdFormat adFormat = AdFormat.fromIntent(intent);
        if(adListener != null)
            adListener.onAdAvailable();

		switch (adFormat) {
			case OFFER_WALL:
				//in our sample app, we want to show the offer wall in a single step.

				break;
			default:
				//we only animate the button if it is not an Offer Wall Intent.

				break;
		}
	}

	@Override
	public void onAdNotAvailable(AdFormat adFormat) {
		FyberLogger.d(getLogTag(), "No ad available");
		resetRequestingState();
		resetIntent();
        if(adListener != null)
            adListener.onAdNotAvailable();
	}

	@Override
	public void onRequestError(RequestError requestError) {
		FyberLogger.d(getLogTag(), "Semething went wrong with the request: " + requestError.getDescription());
		resetRequestingState();
		resetIntent();
        if(adListener != null)
            adListener.onRequestError(requestError.name() + " \n" + requestError.getDescription());
	}

	/*
	* ** State helper methods **
	*/

	private void resetRequestingState() {
		isRequestingState = false;
	}

	private void resetIntent() {
		intent = null;
	}

	protected boolean isIntentAvailable() {
		return intent != null;
	}

	protected boolean isRequestingState() {
		return isRequestingState;
	}

	protected void setToRequestingMode() {
		isRequestingState = true;
	}

	@Override
    public void setAdListener(AdRequestCallback adListener){
        this.adListener = adListener;
    }

}
