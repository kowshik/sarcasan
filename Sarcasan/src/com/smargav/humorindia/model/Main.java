package com.smargav.humorindia.model;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		InputStream stream = Main.class.getResourceAsStream("data.json");
		
		String json = convertToString(stream);
		
		try {
			JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();
			
			PageFeed feed = new PageFeed(obj);
			
			System.out.println(feed.getPosts().size());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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

}
