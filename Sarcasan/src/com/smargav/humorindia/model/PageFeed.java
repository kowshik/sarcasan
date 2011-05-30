package com.smargav.humorindia.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class PageFeed extends Property {

	private List<WallPost> posts;
	private String nextLink;
	private String previousLink;

	public PageFeed(JSONObject jsonObject) {
		super(jsonObject);
		init();
	}
	
	public PageFeed(String json){
		
	}

	private void init() {
		JSONObject paging = getObject("paging");

		try {
			nextLink = paging.getString("next");
			previousLink = paging.getString("previous");

			JSONArray array = getArray("data");

			posts = new ArrayList<WallPost>();

			for (int i = 0; i < array.length(); i++) {
				WallPost post = new WallPost(array.getJSONObject(i));
				System.out.println("Adding wall post : " + post.getTotalComments());
				posts.add(post);

			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	}

	public List<WallPost> getPosts() {
		return posts;
	}

	public void setPosts(List<WallPost> posts) {
		this.posts = posts;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getPreviousLink() {
		return previousLink;
	}

	public void setPreviousLink(String previousLink) {
		this.previousLink = previousLink;
	}

}
