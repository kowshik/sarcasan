package com.smargav.humorindia.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smargav.humorindia.R;
import com.smargav.humorindia.activity.PostsActivity;
import com.smargav.humorindia.model.FacebookWrapper;
import com.smargav.humorindia.model.Likes;
import com.smargav.humorindia.model.PageFeed;
import com.smargav.humorindia.model.WallPost;
import com.smargav.humorindia.util.Constants;

public class EndlessPostsAdapter extends BaseAdapter {

	private LayoutInflater inflator;
	private PostsActivity activity;
	private PageFeed pageFeed;

	private HashMap<Integer, View> finalView;

	public EndlessPostsAdapter(PostsActivity activity, PageFeed feed) {
		this.activity = activity;
		this.inflator = activity.getLayoutInflater();
		this.pageFeed = feed;
		finalView = new HashMap<Integer, View>();
	}

	public int getCount() {
		return pageFeed.getPosts().size();
	}

	public Object getItem(int position) {

		return pageFeed.getPosts().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("Getting: " + position);

		if (finalView.containsKey(position))
			return finalView.get(position);

		WallPost post = (WallPost) getItem(position);
		ViewDataHolder holder = new ViewDataHolder();

		holder.post = post;

		convertView = inflator.inflate(R.layout.posts_list_item, null);

		holder.displayPic = (ImageView) convertView
				.findViewById(R.id.post_display_pic);
		holder.description = (TextView) convertView
				.findViewById(R.id.post_description);
		holder.likesText = (TextView) convertView.findViewById(R.id.likesText);

		// String url = p
		// Bitmap bmp = UiUtil.loadImageFromURL(url);
		// holder.displayPic.setImageBitmap(bmp);

		holder.displayPic.setImageBitmap(Constants.profilePicture);
		holder.description.setText(post.get("message"));

		String likesString = "| " + post.getTotalLikes() + " likes";
		List<Likes> lList = post.getLikesList();
		JSONObject aboutMe = FacebookWrapper.getInstance().getAboutMe();
		boolean doLike = false;
		if (aboutMe != null)
			for (Likes likes : lList) {
				try {
					if (likes.get("id").equals(aboutMe.get("id"))) {
						likesString = "You & " + (post.getTotalLikes())
								+ " like this";
						doLike = true;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		holder.likeButton = (CustomLinkButton) convertView
		.findViewById(R.id.likeButton);
		if (!doLike) {
			holder.likeButton.setOnClickListener(activity);
			holder.likeButton.setTag(holder);
		} else
			holder.likeButton.setVisibility(View.INVISIBLE);

		holder.likesText.setText(likesString);
		convertView.setTag(holder);

		if (!finalView.containsKey(position))
			finalView.put(position, convertView);
		return convertView;
	}

	public static class ViewDataHolder {
		public ImageView displayPic;
		public CustomLinkButton likeButton;

		public TextView description, likesText;

		public WallPost post;
	}

}
