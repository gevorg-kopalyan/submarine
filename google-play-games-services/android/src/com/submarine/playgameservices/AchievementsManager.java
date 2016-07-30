package com.submarine.playgameservices;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Gev on 7/29/2016.
 */
public class AchievementsManager {

    private GoogleApiClient mGoogleApiClient;

    public AchievementsManager(GoogleApiClient mGoogleApiClient){
        this.mGoogleApiClient = mGoogleApiClient;
    }

    //@Override
    public void unlockAchievement(final String achievementId) {
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
                }
            });
        }
    }

    //@Override
    public void incrementAchievement(final String achievementId, final int incrementAmount) {
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Games.Achievements.increment(gameHelper.getApiClient(), achievementId, incrementAmount);
                }
            });
        }
    }

    //@Override
    public void showAchievements() {
        //Gdx.app.log(TAG, "Show Achievements : " + isSignedIn());
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //Gdx.app.log(TAG, "Show Achievements");
                    waitingToShowAchievements = false;
                    activity.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 1);
                }
            });
        } else {
            waitingToShowAchievements = true;
            gameHelper.beginUserInitiatedSignIn();
        }
    }
}
