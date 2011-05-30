package com.smargav.humorindia.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WallPost extends Property {

	private List<Comment> commentsList;
	private List<Likes> likesList;
	private int totalLikes;
	private int totalComments;
	private From from;

	public WallPost(JSONObject jsonObject) {
		super(jsonObject);
		init();
	}

	private void init() {

		from = new From(getObject("from"));

		likesList = new ArrayList<Likes>();
		commentsList = new ArrayList<Comment>();
		if (hasKey("likes"))
			try {
				JSONObject likesObject = getObject("likes");
				totalLikes = likesObject.getInt("count");

				JSONArray array = likesObject.getJSONArray("data");

				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					Likes likes = new Likes(obj);
					likesList.add(likes);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (hasKey("comments"))
			try {
				JSONObject commentsObject = getObject("comments");
				totalComments = commentsObject.getInt("count");

				JSONArray array = commentsObject.getJSONArray("data");

				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					Comment c = new Comment(obj);
					commentsList.add(c);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

	}

	public static class From extends Property {

		public From(JSONObject jsonObject) {
			super(jsonObject);
		}

	}

	public List<Comment> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comment> commentsList) {
		this.commentsList = commentsList;
	}

	public List<Likes> getLikesList() {
		return likesList;
	}

	public void setLikesList(List<Likes> likesList) {
		this.likesList = likesList;
	}

	public int getTotalLikes() {
		return totalLikes;
	}

	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

}
