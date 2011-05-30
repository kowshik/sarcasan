package com.smargav.humorindia.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class Util {

	public static boolean isNull(String s) {
		s = s.trim();
		return s == null || s.equals("") || s.equals("null");
	}

	public static void launchBrowser(Activity activity, String url) {
		System.out.println("Launching for ??? " + url);
		if (!url.startsWith("http://"))
			url = "http://" + url;
		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(myIntent);
	}

	public static void makeCall(Activity activity, CharSequence phoneNumber) {

		Uri uri = Uri.fromParts("tel", phoneNumber.toString(), null);
		Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(callIntent);
	}

	public static String generateRSSURL(String rssURL) {

		String retVal = "";

		rssURL = rssURL.replaceAll("=", "%3D");

		rssURL = rssURL.replaceAll("&", "%26");

		retVal = "http://rss.bloople.net/?url=" + rssURL
				+ "&striphtml=false&type=html";

		System.out.println("Return value: " + retVal);
		return retVal;

	}

	public static String getFromURL(String fileUrl) {
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();

			return convertToString(is);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String convertToString(InputStream stream) {
		try {
			StringBuffer result = new StringBuffer();
			byte[] b = new byte[100];
			int l = 0;
			while ((l = stream.read(b)) != -1)
				result.append(new String(b, 0, l));

			return result.toString();
		} catch (Exception e) {

		}
		return "";
	}

	public static String MD5_Hash(String s) {
		MessageDigest m = null;

		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		m.update(s.getBytes(), 0, s.length());
		String hash = new BigInteger(1, m.digest()).toString(16);
		return hash;
	}

}
