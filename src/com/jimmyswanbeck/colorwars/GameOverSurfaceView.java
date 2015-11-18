package com.jimmyswanbeck.colorwars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameOverSurfaceView extends SurfaceView implements Runnable {
	private GameOverActivity activity;
	private Thread thread = null;
	boolean isRunning = false;
	boolean isHighScore = false;

	int score = 0;

	// Location of Menu button
	int btnMenuX = 0;
	int btnMenuY = 0;

	// Image assets
	Bitmap background = null;
	Bitmap btnMenu = null;
	Bitmap logo = null;
	Bitmap highScore = null;
	
	// Sound assets
    MediaPlayer game_over = null;

	public GameOverSurfaceView(GameOverActivity gameOverActivity) {
		super(gameOverActivity);
		activity = gameOverActivity;

		// Set image assets
		background = DisplayAdvisor.loadScaledToIdeal(gameOverActivity.getResources(), 700, 1050,R.drawable.game_over);
		logo = DisplayAdvisor.loadScaledToIdeal(gameOverActivity.getResources(), 640, 300,R.drawable.game_over_logo);
		btnMenu = DisplayAdvisor.loadScaledToIdeal(gameOverActivity.getResources(), 288, 92, R.drawable.menu);
		highScore = DisplayAdvisor.loadScaledToIdeal(gameOverActivity.getResources(), 460, 110,R.drawable.high_score);

		// Set menu button location
		btnMenuX = (DisplayAdvisor.getX() / 2) - (btnMenu.getWidth() / 2);
		btnMenuY = (4 * DisplayAdvisor.getY() / 7)
				+ (3 * btnMenu.getHeight() / 2);

		score = activity.score;	// Get final score from activity

		isHighScore = checkHighScore();
		
		// Play game over music
		game_over = MediaPlayer.create(gameOverActivity, R.raw.game_over);
		game_over.start();
	}

	private boolean checkHighScore() {
		int highScore = Storage.readScore();
		if (score > highScore) {
			Storage.writeScore(score);
			return true;
		}
		return false;
	}

	public void run() {
		while (isRunning) {
			SurfaceHolder surfaceHolder = getHolder();
			if (surfaceHolder.getSurface().isValid()) {
				Canvas canvas = surfaceHolder.lockCanvas();
				drawEverything(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}

			// Get touch location
			if (activity.wasTouched) {
				Point point = activity.getTouch();
				
				// If player touched the Menu button, close the activity
				if (checkSquareTouchLocation(point, btnMenu, btnMenuX, btnMenuY)) {
					this.activity.finish();
					this.activity.overridePendingTransition(0, 0);
				}
			}
		}
	}

	// Returns true or false about whether the player touched within a square location
	private boolean checkSquareTouchLocation(Point point, Bitmap element, int elementStartX, int elementStartY) {
		if (point.x > elementStartX
				&& point.x < elementStartX + element.getWidth()
				&& point.y > elementStartY
				&& point.y < elementStartY + element.getHeight()) {
			return true;
		}
		return false;
	}

	private void drawEverything(Canvas canvas) {
		// Background
		canvas.drawARGB(255, 0, 0, 0);
		canvas.drawBitmap(background,
				(DisplayAdvisor.getX() / 2 - background.getWidth() / 2), 0,
				null);
		
		canvas.drawBitmap(btnMenu, btnMenuX, btnMenuY, null);	// Menu button
		canvas.drawBitmap(logo,
				(DisplayAdvisor.getX() / 2 - logo.getWidth() / 2),
				DisplayAdvisor.getY() / 3 - logo.getHeight() / 2, null);	// Game Over label

		// If the player got a high score, display the high score label
		if (isHighScore) {
			canvas.drawBitmap(highScore,
					(DisplayAdvisor.getX() / 2 - highScore.getWidth() / 2),
					btnMenuY - (3 * highScore.getHeight() / 2), null);
		}

		// Score text
		Paint scoreText = new Paint();
		scoreText.setARGB(255, 0, 0, 0);
		scoreText.setTextSize(DisplayAdvisor.getY() / 20);
		canvas.drawText(
				getResources().getString(R.string.score)
						+ Integer.toString(score),
				(DisplayAdvisor.getX() / 2)
						- (scoreText.measureText("Score: "
								+ Integer.toString(score)) / 2),
				1 * DisplayAdvisor.getY() / 2, scoreText);
	}

	public void onPause() {
		isRunning = false;
		while (thread != null) {
			try {
				thread.join();
				thread = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void onResume() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
}
