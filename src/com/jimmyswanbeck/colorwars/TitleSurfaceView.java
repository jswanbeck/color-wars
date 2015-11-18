package com.jimmyswanbeck.colorwars;

import com.jimmyswanbeck.colorwars.Settings.Difficulty;
import com.jimmyswanbeck.colorwars.Settings.GameMode;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TitleSurfaceView extends SurfaceView implements Runnable {
	private MainActivity activity;
    private Thread thread = null;
	boolean isRunning = false;
	
	// Settings
	GameMode gameMode;
	Difficulty difficulty;
	
	// Start button location
	int btnStartX = 0;
	int btnStartY = 0;
	
	// Game Mode button location
	int btnGameModeX = 0;
	int btnGameModeY = 0;
	
	// Difficulty button location
	int btnDifficultyX = 0;
	int btnDifficultyY = 0;
	
	// Image assets
    Bitmap background = null;
    Bitmap logo = null;
    Bitmap btnStart = null;
    Bitmap btnDifficulty = null;
    Bitmap btnEasy = null;
    Bitmap btnMedium = null;
    Bitmap btnHard = null;
    Bitmap btnGameMode = null;
    Bitmap btnMatch = null;
    Bitmap btnComplement = null;

	public TitleSurfaceView(MainActivity context) {
		super(context);
		activity = context;
		
		// Set settings
		gameMode = Storage.readGameMode();
		difficulty = Storage.readDifficulty();
		
		// Set image assets
		background = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 700, 1050, R.drawable.title_background);
		logo = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 640, 300, R.drawable.title_logo);
		btnStart = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.start);
		btnEasy = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.easy);
		btnMedium = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.medium);
		btnHard = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.hard);
		btnMatch = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.match);
		btnComplement = DisplayAdvisor.loadScaledToIdeal(context.getResources(), 288, 92, R.drawable.complement);
		
		// Set button images based on settings
		if (difficulty == Difficulty.EASY) {
			btnDifficulty = btnEasy;
		}
		else if (difficulty == Difficulty.MEDIUM) {
			btnDifficulty = btnMedium;
		}
		else {
			btnDifficulty = btnHard;
		}
		
		if (gameMode == GameMode.MATCH) {
			btnGameMode = btnMatch;
		}
		else {
			btnGameMode = btnComplement;
		}
		
		// Set button locations
		btnStartX = (DisplayAdvisor.getX() / 2) - (btnStart.getWidth() / 2);
		btnStartY = (4 * DisplayAdvisor.getY() / 8) + (btnStart.getHeight() / 2);
		
		btnDifficultyX = (DisplayAdvisor.getX() / 2) - (btnDifficulty.getWidth() / 2);
		btnDifficultyY = (5 * DisplayAdvisor.getY() / 8) + (btnDifficulty.getHeight() / 2);
		
		btnGameModeX = (DisplayAdvisor.getX() / 2) - (btnGameMode.getWidth() / 2);
		btnGameModeY = (6 * DisplayAdvisor.getY() / 8) + (btnGameMode.getHeight() / 2);
	}
	
	public void run() {
		while (isRunning) {
			SurfaceHolder surfaceHolder = getHolder();
			if (surfaceHolder.getSurface().isValid()) {
				Canvas canvas = surfaceHolder.lockCanvas();
				drawEverything(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
			
			// Button logic
			if (activity.wasTouched) {
				Point point = activity.getTouch();
				
				// Start button
				if (checkSquareTouchLocation(point, btnStart, btnStartX, btnStartY)) {
					Intent myIntent = new Intent(this.activity, GameActivity.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					myIntent.putExtra("gameMode", gameMode);
					myIntent.putExtra("difficulty", difficulty);
					this.activity.startActivity(myIntent);
				}
				// Difficulty button
				else if (checkSquareTouchLocation(point, btnDifficulty, btnDifficultyX, btnDifficultyY)) {
					if (difficulty == Difficulty.EASY) {
						difficulty = Difficulty.MEDIUM;
						btnDifficulty = btnMedium;
						Storage.writeDifficulty(Difficulty.MEDIUM);
					}
					else if (difficulty == Difficulty.MEDIUM) {
						difficulty = Difficulty.HARD;
						btnDifficulty = btnHard;
						Storage.writeDifficulty(Difficulty.HARD);
					}
					else {
						difficulty = Difficulty.EASY;
						btnDifficulty = btnEasy;
						Storage.writeDifficulty(Difficulty.EASY);
					}
				}
				// Game Mode button
				else if (checkSquareTouchLocation(point, btnGameMode, btnGameModeX, btnGameModeY)) {
					if (gameMode == GameMode.MATCH) {
						gameMode = GameMode.COMPLEMENT;
						btnGameMode = btnComplement;
						Storage.writeGameMode(GameMode.COMPLEMENT);
					}
					else {
						gameMode = GameMode.MATCH;
						btnGameMode = btnMatch;
						Storage.writeGameMode(GameMode.MATCH);
					}
				}
			}
		}
	}

	// Check if the player tapped between four points
	private boolean checkSquareTouchLocation(Point point, Bitmap element, int elementStartX, int elementStartY) {
		if (point.x > elementStartX && point.x < elementStartX + element.getWidth() && point.y > elementStartY && point.y < elementStartY + element.getHeight()) {
			return true;
		}
		return false;
	}

	// Draw all image assets
	private void drawEverything(Canvas canvas) {
		canvas.drawARGB(255, 255, 255, 255);
		canvas.drawBitmap(background, (DisplayAdvisor.getX()/2 - background.getWidth()/2), 0, null);
		canvas.drawBitmap(logo, (DisplayAdvisor.getX()/2 - logo.getWidth()/2), DisplayAdvisor.getY()/3 - logo.getHeight()/2, null);
		canvas.drawBitmap(btnStart, btnStartX, btnStartY, null);
		canvas.drawBitmap(btnDifficulty, btnDifficultyX, btnDifficultyY, null);
		canvas.drawBitmap(btnGameMode, btnGameModeX, btnGameModeY, null);
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
