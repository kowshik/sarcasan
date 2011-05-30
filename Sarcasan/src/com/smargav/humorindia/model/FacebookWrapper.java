package com.smargav.humorindia.model;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.smargav.humorindia.util.Constants;
import com.smargav.humorindia.util.HTTPUtil;

public class FacebookWrapper {

	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "publish_checkins", "offline_access", "email",
			"read_stream" };
	private static FacebookWrapper INSTANCE = null;

	private JSONObject aboutMe = null;

	public static FacebookWrapper getInstance() {
		if (INSTANCE == null)
			INSTANCE = new FacebookWrapper();
		return INSTANCE;
	}

	Facebook facebook = new Facebook(Constants.APP_ID);
	protected boolean isAuthorized;

	public FacebookWrapper() {

	}

	public void startAuth(final Activity activity, DialogListener dlgL) {

		if (dlgL == null)
			dlgL = new DialogListener() {

				public void onComplete(Bundle values) {
					isAuthorized = true;
					try {
						aboutMe = new JSONObject(facebook.request("/me"));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void onFacebookError(FacebookError error) {
					isAuthorized = false;
				}

				public void onError(DialogError e) {
					isAuthorized = false;
				}

				public void onCancel() {
					isAuthorized = false;
				}
			};

		facebook.authorize(activity, PERMISSIONS, dlgL);
	}

	public void startAuth(Activity activity) {
		startAuth(activity, null);
	}

	public void authorizeCallback(int requestCode, int resultCode, Intent data) {
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	public String getFeeds() throws MalformedURLException, IOException {
		String json = facebook.request(Constants.sarcasanPageId + "/feed");
		System.out.println("Returned :::    " + json);
		return json;
	}

	public boolean isAuthorized() {
		return facebook.isSessionValid();
	}

	public String request(String r) throws MalformedURLException, IOException {
		return facebook.request(r);

	}

	public String getAT() {
		return facebook.getAccessToken();
	}

	public String post(String URL, String[] keys, String[] values) {

		HTTPUtil.postData(URL, keys, values);
		return null;

	}

	public JSONObject getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(JSONObject jsonObject) {
		aboutMe = jsonObject;
		
	}
	
	

}
