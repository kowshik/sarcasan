package com.smargav.humorindia.adapter;

import android.view.View;
import android.view.View.OnClickListener;

import com.smargav.humorindia.adapter.EndlessPostsAdapter.ViewDataHolder;
import com.smargav.humorindia.model.FacebookWrapper;

public class LikeButtonListener implements OnClickListener {

	FacebookWrapper fbWrapper = FacebookWrapper.getInstance();
	ViewDataHolder holder;

	public LikeButtonListener(ViewDataHolder holder) {
		this.holder = holder;
	}

	public void onClick(View view) {
		
	}

}
