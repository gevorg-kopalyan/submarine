package com.submarine.fyber;

import android.app.Activity;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;

/**
 * Created by Gev on 5/2/2016.
 */

public class FyberAndroidNetwork implements FyberNetworkCore{



    private final Activity activity;
    private final InterstitialManager interstitialManager;
    private final RewardedVideoManager rewardedVideoManager;

    public FyberAndroidNetwork(Activity activity, final String appId) {
        this.activity = activity;
        Fyber.with(appId, activity)
                .start();
        FyberLogger.enableLogging(BuildConfig.DEBUG);
        interstitialManager = new InterstitialManager(activity);
        rewardedVideoManager = new RewardedVideoManager(activity);
    }

    public InterstitialManager getInterstitialManager(){
        return interstitialManager;
    }

    public RewardedVideoManager getRewardedVideoManager(){
        return rewardedVideoManager;
    }
}
