package com.smargav.humorindia.activity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListAdapter;

import com.facebook.android.DialogError;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.smargav.humorindia.R;
import com.smargav.humorindia.adapter.EndlessAdapter;
import com.smargav.humorindia.adapter.EndlessPostsAdapter;
import com.smargav.humorindia.adapter.EndlessPostsAdapter.ViewDataHolder;
import com.smargav.humorindia.model.FacebookWrapper;
import com.smargav.humorindia.model.PageFeed;
import com.smargav.humorindia.model.Parser;
import com.smargav.humorindia.model.WallPost;
import com.smargav.humorindia.util.GlobalValues;
import com.smargav.humorindia.util.HTTPUtil;
import com.smargav.humorindia.util.UiUtil;
import com.smargav.humorindia.util.Util;

public class PostsActivity extends ListActivity implements OnClickListener {

	private PageFeed pageFeed;
	private ProgressDialog progressDialog;

	public PostsActivity() {
		// TODO Auto-generated constructor stub
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posts);
		initData();
		Intent intent = getIntent();
		String postsData = intent.getStringExtra("feed");
		System.out.println("FEED : " + postsData);
		pageFeed = com.smargav.humorindia.model.Parser.parse(postsData);
		EndlessPostsAdapter adapter = new EndlessPostsAdapter(this, pageFeed);
		setListAdapter(new PostsAdapter(adapter));
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case GlobalValues.PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("");
			return progressDialog;
		case GlobalValues.ERROR_DIALOG:
			return UiUtil.showErrorDialog("Error performing action.", this);
		default:
			return null;
		}
	}

	private void initData() {

	}

	private class PostsAdapter extends EndlessAdapter {
		private RotateAnimation rotate = null;

		public PostsAdapter(ListAdapter wrapped) {
			super(wrapped);

			rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
					0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotate.setDuration(600);
			rotate.setRepeatMode(Animation.RESTART);
			rotate.setRepeatCount(Animation.INFINITE);
		}

		protected View getPendingView(ViewGroup parent) {
			View row = getLayoutInflater().inflate(R.layout.row, null);
			View child = row.findViewById(android.R.id.text1);
			child.setVisibility(View.GONE);
			child = row.findViewById(R.id.throbber);
			child.setVisibility(View.VISIBLE);
			child.startAnimation(rotate);
			return (row);
		}

		protected void appendCachedData() {

		}

		protected boolean cacheInBackground() throws Exception {

			try {
				String nextLink = pageFeed.getNextLink();
				String feed = Util.convertToString(HTTPUtil.performGET(
						nextLink, new String[] {}, new String[] {}));
				PageFeed pf = Parser.parse(feed);
				pageFeed.getPosts().addAll(pf.getPosts());
				pageFeed.setNextLink(pf.getNextLink());
				pageFeed.setPreviousLink(pf.getPreviousLink());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	}

	FacebookWrapper fbWrapper = FacebookWrapper.getInstance();

	public void onClick(View v) {

		showLikeDialog(v);

	}

	private void showLikeDialog(View v) {

		final ViewDataHolder holder = (ViewDataHolder) v.getTag();

		if (!fbWrapper.isAuthorized())
			fbWrapper.startAuth(this, new DialogListener() {

				public void onFacebookError(FacebookError e) {

				}

				public void onError(DialogError e) {
					dismissDialog(GlobalValues.PROGRESS_DIALOG);

				}

				public void onComplete(Bundle values) {
					try {
						fbWrapper.setAboutMe(new JSONObject(fbWrapper
								.request("/me")));
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
					doLike(holder);

				}

				public void onCancel() {
					dismissDialog(GlobalValues.PROGRESS_DIALOG);

				}
			});
		if (fbWrapper.isAuthorized()) {
			showDialog(GlobalValues.PROGRESS_DIALOG);
			doLike(holder);
			dismissDialog(GlobalValues.PROGRESS_DIALOG);

		}

	}

	private void doLike(ViewDataHolder holder) {

		performLikeAction(holder.post);
		WallPost post = holder.post;
		holder.likesText.setText("You & " + (post.getTotalLikes())
				+ " like this");
		holder.likeButton.setVisibility(View.INVISIBLE);

	}

	private void performLikeAction(WallPost post) {
		// /OBJECT_ID/likes

		String id = post.get("id");

		try {

			String[] keys = { "access_token" };
			String[] values = { fbWrapper.getAT() };
			String likeURL = "https://graph.facebook.com/" + id + "/likes";

			String data = Util.convertToString(HTTPUtil.postData(likeURL, keys,
					values));

			System.out.println(data);
			// System.out.println(fbWrapper.request(id + "/likes"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fbWrapper.authorizeCallback(requestCode, resultCode, data);
	}

}
