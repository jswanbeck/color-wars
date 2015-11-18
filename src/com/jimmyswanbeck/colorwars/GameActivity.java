package com.jimmyswanbeck.colorwars;

import com.jimmyswanbeck.colorwars.Settings.Difficulty;
import com.jimmyswanbeck.colorwars.Settings.GameMode;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class GameActivity extends Activity {
	GameSurfaceView surfaceView;
    Point touched;
    boolean wasTouched;
    GameMode gameMode;
    Difficulty difficulty;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		DisplayAdvisor.setScreenDimensions(displaymetrics);
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		gameMode = (GameMode) intent.getSerializableExtra("gameMode");
		difficulty = (Difficulty) intent.getSerializableExtra("difficulty");
		
		touched = new Point();
		
		surfaceView = new GameSurfaceView(this);
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
