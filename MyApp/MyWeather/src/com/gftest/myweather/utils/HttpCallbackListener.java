package com.gftest.myweather.utils;

public interface HttpCallbackListener {

	public void onFinished(String response);

	public void onError(Exception exception);

}
