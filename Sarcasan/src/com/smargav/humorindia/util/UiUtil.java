package com.smargav.humorindia.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smargav.humorindia.R;

public class UiUtil {

	public static Dialog askQuitAlert(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Do you really want to exit?").setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								activity.finish();
							}
						}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		return alert;

	}

	public static Dialog showAboutMessageDialog(Activity ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		View about = ctx.getLayoutInflater().inflate(R.layout.about_message,
				null);
		builder.setView(about).setCancelable(true).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

	public static Dialog showErrorDialog(String message, Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setCancelable(true).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

	private static Map<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

	public static Bitmap loadImageFromURL(String imageUrl) {

		if (bitmapHash.containsKey(imageUrl))
			return bitmapHash.get(imageUrl);

		URL myFileUrl = null;
		try {
			myFileUrl = new URL(imageUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(is);
			bitmapHash.put(imageUrl, bmp);

			return bmp;
			// imView.setImageBitmap(bmImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static TextView getTextView(Activity ctx, int id) {
		return (TextView) ctx.findViewById(id);
	}

}
