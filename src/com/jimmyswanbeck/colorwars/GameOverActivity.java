package com.jimmyswanbeck.colorwars;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class GameOverActivity extends Activity {
	GameOverSurfaceView surfaceView;
    Point touched;
    boolean wasTouched;
    
    int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		DisplayAdvisor.setScreenDimensions(displaymetrics);
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		score = intent.getIntExtra("score", 0);
		
		touched = new Point();
		
		surfaceView = new GameOverSurfaceView(this);
		setContentView(surfaceView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		surfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		surfaceView.onResume();
	}
    
	@Override
    public void onBackPressed() {
      this.finish();
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
			wasTouched = true;
			touched.x = (int) e.getX();
			touched.y = (int) e.getY();
			break;
		case MotionEvent.ACTION_UP:
			wasTouched = true;
			touched.x = (int) e.getX();
			touched.y = (int) e.getY();
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
