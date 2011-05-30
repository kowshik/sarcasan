package com.smargav.humorindia.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Parser {

	public static PageFeed parse(String json) {

		try {
			JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();

			PageFeed feed = new PageFeed(obj);
			return feed;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
