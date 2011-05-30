package com.smargav.humorindia.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.smargav.humorindia.R;
import com.smargav.humorindia.util.Constants;
import com.smargav.humorindia.util.HTTPUtil;
import com.smargav.humorindia.util.UiUtil;
import com.smargav.humorindia.util.Util;

public class StartupActivity extends Activity {
	protected boolean _active = true;
	protected int _splashTime = 2000;
	AnimationSet rootSet;
	private Class nextActivityClass;

	ImageView splash;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup_splash);

		// graphics = (SplashView) findViewById(R.id.graphics);
		splash = (ImageView) findViewById(R.id.splashImage);
		rootSet = getAnimation();

		Thread splashTread = new Thread() {

			public void run() {

				splash.startAnimation(rootSet);

				try {
					String[] keys = new String[] {};
					String[] values = new String[] {};
					String pagedata = Util.convertToString(HTTPUtil.performGET(
							Constants.baseUrl, keys, values));

					JSONObject pageData = new JSONObject(pagedata);

					String imgUrl = pageData.getString("picture");

					Constants.profilePicture = UiUtil.loadImageFromURL(imgUrl);
					String feed = Util.convertToString(HTTPUtil.performGET(
							Constants.feedsUrl, keys, values));
					Intent intent = new Intent(StartupActivity.this,
							PostsActivity.class);
					intent.putExtra("feed", feed);
					startActivity(intent);

				} catch (Exception e) {

					e.printStackTrace();
				}

				finish();

				return;

			}
		};
		splashTread.start();

	}

	protected void loadNextActivity() {
		// Intent intent = new Intent(this, LoginActivity.class);
		Intent intent = new Intent(this, nextActivityClass);
		startActivity(intent);
	}

	private AnimationSet getAnimation() {
		rootSet = new AnimationSet(true);
		rootSet.setInterpolator(new BounceInterpolator());

		TranslateAnimation trans1 = new TranslateAnimation(0, 0, -400, 0);
		trans1.setStartOffset(0);
		trans1.setDuration(200);
		trans1.setFillAfter(true);
		rootSet.addAnimation(trans1);

		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				ScaleAnimation.RELATIVE_TO_PARENT, 0.5f,
				ScaleAnimation.RELATIVE_TO_PARENT, 0.5f);
		scale.setDuration(200);
		scale.setFillAfter(true);
		AnimationSet childSet = new AnimationSet(true);
		childSet.addAnimation(scale);
		childSet.setInterpolator(new BounceInterpolator());
		rootSet.addAnimation(childSet);

		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setStartOffset(4000);
		outtoRight.setDuration(400);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		rootSet.addAnimation(outtoRight);

		return rootSet;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_active = false;
		}
		return true;
	}

	// Define the Handler that receives messages from the thread and update
	// the progress
	final Handler handler = new Handler();

}