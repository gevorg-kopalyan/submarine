package com.submarine.applovin;

import java.util.Map;

/**
 * Created by Gev on 4/6/2016.
 */
public interface AppLovinRewardVideoListener {
    public void userRewardVerified(Map<String, String> response);
    public void userOverQuota(Map<String, String> response);
    public void userRewardRejected(Map<String, String> response);
    public void validationRequestFailed(int errorCode);
    public void userDeclinedToViewAd();
    public void adReceived();
    public void failedToReceiveAd(int errorCode);
}
