package com.smargav.humorindia.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomLinkButton extends Button {

	public CustomLinkButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomLinkButton(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public CustomLinkButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	

	public void onDraw(Canvas canvas) {
		if (isFocused())
			canvas.drawARGB(180, 0, 100, 255);
		else
			canvas.drawARGB(128, 243, 243, 243);
		super.onDraw(canvas);

	}

}
