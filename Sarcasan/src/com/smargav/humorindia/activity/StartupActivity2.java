package com.smargav.humorindia.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.smargav.humorindia.R;
import com.smargav.humorindia.model.FacebookWrapper;
import com.smargav.humorindia.util.GlobalValues;
import com.smargav.humorindia.util.UiUtil;

public class StartupActivity2 extends Activity {

	FacebookWrapper fbWrapper = FacebookWrapper.getInstance();

	private ProgressDialog progressDialog;

	private ProgressThread progressThread;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// fbWrapper.startAuth(this);
		setContentView(R.layout.startup);
		showDialog(GlobalValues.PROGRESS_DIALOG);	
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fbWrapper.authorizeCallback(requestCode, resultCode, data);
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case GlobalValues.PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Loading Feeds...");
			return progressDialog;
		case GlobalValues.ERROR_DIALOG:
			return UiUtil.showErrorDialog("Error loading feeds...", this);
		default:
			return null;
		}
	}

	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case GlobalValues.PROGRESS_DIALOG:
			progressDialog.setProgress(0);
			progressThread = new ProgressThread(new Handler());
			progressThread.start();
		}

	}

	private class ProgressThread extends Thread {

		public ProgressThread(Handler handler) {
			try {
				Thread.sleep(10000);
				String feed = fbWrapper.getFeeds();

				Intent intent = new Intent(StartupActivity2.this,
						PostsActivity.class);
				intent.putExtra("feed", feed);
				startActivity(intent);
				finish();

			} catch (Exception e) {
				e.printStackTrace();

			}

			dismissDialog(GlobalValues.PROGRESS_DIALOG);
		}
	}
}