package com.submarine.applovin;

import com.submarine.supersonic.*;

/**
 * Created by Gev on 2/6/15.
 */
public class IOSAppLovinNetwork implements SuperSonicNetwork {

    private SuperSonicInterstitial interstitialManager;
    private SuperSonicRewardedVideo rewardedVideoManager;

    public IOSAppLovinNetwork() {
        initInterstitialManager();
        initRewardedVideoManager();
    }

    @Override
    public void initInterstitialManager() {
        interstitialManager = new SuperSonicInterstitial(){

            @Override
            public void setAdlistener(SuperSonicInterstitialAdListener listener) {

            }

            @Override
            public void loadInterstitial() {

            }

            @Override
            public void showInterstitial() {

            }

            @Override
            public void showInterstitial(String placementName) {

            }
        };
    }

    @Override
    public void initRewardedVideoManager() {
        rewardedVideoManager = new SuperSonicRewardedVideo() {
            @Override
            public boolean isRewardedVideoAvailable() {
                return false;
            }

            @Override
            public void showRewardedVideo() {

            }

            @Override
            public void showRewardedVideo(String placementName) {

            }

            @Override
            public void setRewardedVideoListener(SuperSonicRewardedVideoListener listener) {

            }
        };
    }

    @Override
    public SuperSonicInterstitial getInterstitialManager() {
        return interstitialManager;
    }

    @Override
    public SuperSonicRewardedVideo getRewardedVideoManager() {
        return rewardedVideoManager;
    }
}
