package com.submarine.fyber;

import android.app.Activity;
import com.fyber.requesters.InterstitialRequester;

public class InterstitialManager extends FyberBase {

	private static final String TAG = InterstitialManager.class.getSimpleName();

	public InterstitialManager(Activity activity) {
		super(activity);
	}

	public void requestInterstitial(){
		requestAd();
	}

	public void showInterstitial(){
		showAd();
	}
	//requestOrShowAd();



	/*
	* ** Code to perform an Interstitial ad request **
	*/

	@Override
	protected void performRequest() {
		//request an interstitial ad.
		InterstitialRequester
				.create(this)
				.request(activity);
	}

	/*
	* ** FyberBase methods **
	*/

	@Override
	public String getLogTag() {
		return TAG;
	}

	@Override
	protected int getRequestCode() {
		return INTERSTITIAL_REQUEST_CODE;
	}

}