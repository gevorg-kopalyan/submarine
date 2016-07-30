package com.submarine.fyber;

/**
 * Created by Gev on 5/6/2016.
 */
public interface AdRequestCallback {
    public void onAdAvailable();
    public void onAdNotAvailable();
    public void onRequestError(String errorMsg);
}
