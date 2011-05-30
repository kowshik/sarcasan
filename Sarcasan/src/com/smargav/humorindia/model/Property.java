package com.smargav.humorindia.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Property {

	private JSONObject jsonObject;

	public Property(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public Property() {

	}

	public JSONObject getJSONObject() {
		return jsonObject;
	}

	public JSONArray getArray(String key) {
		try {
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getObject(String key) {
		try {
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String get(String key) {
		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean hasKey(String key){
		return jsonObject.has(key);
	}
}
