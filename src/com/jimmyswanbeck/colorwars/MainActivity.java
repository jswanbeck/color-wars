package com.jimmyswanbeck.colorwars;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MainActivity extends Activity {
	TitleSurfaceView surfaceView;
    Point touched;
    boolean wasTouched;
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		DisplayAdvisor.setScreenDimensions(displaymetrics);
		
		super.onCreate(savedInstanceState);
		
		touched = new Point();
		
		surfaceView = new TitleSurfaceView(this);
		setContentView(surfaceView);
	}

	protected void onPause() {
		super.onPause();
		surfaceView.onPause();
	}

	protected void onResume() {
		super.onResume();
		surfaceView.onResume();
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		switch(action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			wasTouched = true;
			touched.x = (int) e.getX();
			touched.y = (int) e.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		
		return true;
	}
    
    public boolean wasTouched() {
    	return wasTouched;
    }
    
    public Point getTouch() {
    	wasTouched = false;
    	return touched;
    }
}
