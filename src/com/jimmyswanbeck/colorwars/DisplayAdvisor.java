package com.jimmyswanbeck.colorwars;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;

public class DisplayAdvisor {
	static int maxX;
	static int maxY;	
	static float scaleX;
	static float scaleY;
	final static int IDEAL_WIDTH = 600;
	final static int IDEAL_HEIGHT = 1024;
	
	// Set the scale factor for screen size
	static public void setScreenDimensions(DisplayMetrics displaymetrics) {
		maxX = displaymetrics.widthPixels;
		maxY = displaymetrics.heightPixels;
		
		scaleY = (float) maxY/IDEAL_HEIGHT;
		scaleX = (float) maxX/IDEAL_WIDTH;
		scaleY = Math.min(scaleX, scaleY);
		scaleX = scaleY;
	}
	
	// Return screen width
	static public int getX() {
		return maxX;
	}
	
	// Return screen height
	static public int getY() {
		return maxY;
	}

	// Load a bitmap scaled to the device's screen size
	public static Bitmap loadScaledToIdeal(Resources resources, int idealImgWidth, int idealImgHeight, int id) {
		Options options = new Options();
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		Bitmap bitmap = BitmapFactory.decodeResource(resources, id, options);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int) (idealImgWidth * scaleX), (int) (idealImgHeight * scaleY), false);
		return bitmap;
	}
}
