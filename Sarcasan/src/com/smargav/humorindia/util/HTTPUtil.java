package com.smargav.humorindia.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HTTPUtil {

	public static InputStream performGET(String URL, String[] keys,
			String[] values) throws MalformedURLException, IOException {

		StringBuffer getData = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			getData.append(keys[i] + "=" + values[i]);
			getData.append("&");
		}

		String getURL = URL + "?" + getData;
		System.out.println(getURL);
		InputStream is = (InputStream) new URL(getURL).getContent();
		return is;
	}

	public static InputStream postData(String URL, String[] keys, String[] values) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			for (int i = 0; i < keys.length; i++) {
				nameValuePairs.add(new BasicNameValuePair(keys[i], values[i]));

			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			return response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return null;
	}

}
