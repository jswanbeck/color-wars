package com.jimmyswanbeck.colorwars;

import java.util.ArrayList;
import com.jimmyswanbeck.colorwars.Palette.ColorType;
import com.jimmyswanbeck.colorwars.Settings.Difficulty;
import com.jimmyswanbeck.colorwars.Settings.GameMode;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {	
	private GameActivity activity;
    private Thread thread = null;
	boolean isRunning = false;	
	boolean gameOver = false;
	ArrayList<PlayerMissile> playerMissiles = new ArrayList<>();	// All player missiles on the screen
	ArrayList<EnemyMissile> enemyMissiles = new ArrayList<>();		// All enemy missiles on the screen
	ArrayList<Explosion> explosions = new ArrayList<>();			// All explosion animations that are in progress
	Explosion gameOverExplosion;		// Final explosion to fill the screen; game over animation
	Palette palette = new Palette();
	Resources resources = null;
	int startTimer = 2000;	// Timer to wait to draw the screen
	long startTime;	// Time at which the game was started
	
	// Image assets
    Bitmap background0 = null;
    Bitmap background1 = null;
    Bitmap background2 = null;
    Bitmap background3 = null;
    Bitmap btnQuit = null;
    
    // Sound assets
    MediaPlayer destroy_incoming = null;
    MediaPlayer fail_incoming = null;
    MediaPlayer hit1 = null;
    MediaPlayer hit2 = null;
    MediaPlayer hit3 = null;
    MediaPlayer hit4 = null;
    MediaPlayer intro = null;
	
	ColorType indicator = ColorType.WHITE;
	Paint indicatorPaint = new Paint();
	
	// Game variables
	boolean complement;
	int health = 3;
	int healthBarWidth = 0;
	int healthBar3 = 0;
	int healthBar2 = 0;
	int healthBar1 = 0;
	int score = 0;
	
	int cooldownTimer = 0;
	int spawnTimer = 0;
	int clock = 0;
	int pointValue = 0;
	int fallSpeed = 0;
	
	int paletteHeight = 0;
	int paletteRadius = 0;
	int paletteBar = 0;
	int indicatorRadius = 0;
	int indicatorMaxSize = 0;

	// Button screen locations
	int btnQuitX = 0;
	int btnQuitY = 0;
	int btnRedX = 0;
	int btnRedY = 0;
	int btnYellowX = 0;
	int btnYellowY = 0;
	int btnBlueX = 0;
	int btnBlueY = 0;
	int indicatorX = 0;
	int indicatorY = 0;
    
	// Paint variables
	Paint red = new Paint();
	Paint scarlet = new Paint();
	Paint orange = new Paint();
	Paint vermilion = new Paint();
	Paint yellow = new Paint();
	Paint chartreuse = new Paint();
	Paint green = new Paint();
	Paint turquoise = new Paint();
	Paint blue = new Paint();
	Paint indigo = new Paint();
	Paint purple = new Paint();
	Paint fuchsia = new Paint();
	Paint white = new Paint();
	Paint black = new Paint();

	public GameSurfaceView(GameActivity context) {
		super(context);
		activity = context;
		
		startTime = System.currentTimeMillis();	// Get start time
		
		gameOverExplosion = new Explosion(0, 0, ColorType.BLACK);
		
		// Set paint colors
		palette.setColors();
		red = palette.red;
		scarlet = palette.scarlet;
		orange = palette.orange;
		vermilion = palette.vermilion;
		yellow = palette.yellow;
		chartreuse = palette.chartreuse;
		green = palette.green;
		turquoise = palette.turquoise;
		blue = palette.blue;
		indigo = palette.indigo;
		purple = palette.purple;
		fuchsia = palette.fuchsia;
		white = palette.white;
		black = palette.black;
		
		complement = activity.gameMode == GameMode.COMPLEMENT;	// Get game mode
		pointValue = complement ? 20 : 10;	// Set point value based on game mode
		
		// Set game variables based on difficulty level
		if (activity.difficulty == Difficulty.EASY) {
			cooldownTimer = 80;
			fallSpeed = DisplayAdvisor.getY() / 730;
			spawnTimer = 540;
		}
		else if (activity.difficulty == Difficulty.MEDIUM) {
			cooldownTimer = 60;
			fallSpeed = DisplayAdvisor.getY() / 350;
			spawnTimer = 180;
		}
		else {
			cooldownTimer = 40;
			fallSpeed = DisplayAdvisor.getY() / 250;
			spawnTimer = 140;
		}
		
		clock = spawnTimer;	// Spawn the first missile immediately

		// Set sound assets
		destroy_incoming = MediaPlayer.create(context, R.raw.destroy_incoming);
		fail_incoming = MediaPlayer.create(context, R.raw.fail_incoming);
		hit1 = MediaPlayer.create(context, R.raw.hit1);
		hit2 = MediaPlayer.create(context, R.raw.hit2);
		hit3 = MediaPlayer.create(context, R.raw.hit3);
		hit4 = MediaPlayer.create(context, R.raw.hit4);
		intro = MediaPlayer.create(context, R.raw.intro);
		
		// Set image assets
		resources = context.getResources();
		background0 = DisplayAdvisor.loadScaledToIdeal(resources, 700, 1050, R.drawable.game_0);
		background1 = DisplayAdvisor.loadScaledToIdeal(resources, 700, 1050, R.drawable.game_1);
		background2 = DisplayAdvisor.loadScaledToIdeal(resources, 700, 1050, R.drawable.game_2);
		background3 = DisplayAdvisor.loadScaledToIdeal(resources, 700, 1050, R.drawable.game_3);
		btnQuit = DisplayAdvisor.loadScaledToIdeal(resources, 288, 92, R.drawable.quit);
		
		healthBar3 = DisplayAdvisor.getX();
		healthBar2 = 2 * DisplayAdvisor.getX() / 3;
		healthBar1 = DisplayAdvisor.getX() / 3;
		healthBarWidth = healthBar3;
		
		btnQuitX = 0;
		btnQuitY = 0;
		
		paletteRadius = 40;
		paletteHeight = (int) (DisplayAdvisor.getY() - (1.5 * paletteRadius));
		paletteBar = DisplayAdvisor.getY() - (13 * paletteRadius / 4);
		indicatorRadius = 160;
		indicatorMaxSize = indicatorRadius;
		
		btnRedX = 3 * DisplayAdvisor.getX() / 20;
		btnRedY = paletteHeight;
		
		btnYellowX = 8 * DisplayAdvisor.getX() / 20;
		btnYellowY = paletteHeight;
		
		btnBlueX = 13 * DisplayAdvisor.getX() / 20;
		btnBlueY = paletteHeight;
		
		indicatorX = DisplayAdvisor.getX();
		indicatorY = DisplayAdvisor.getY();
		
		indicatorPaint = white;
		
		intro.start();	// Play intro music
	}

	public void run() {
		while (isRunning) {
			long currentTime = System.currentTimeMillis() - startTime;
			
			// Don't start running or draw the canvas until the game has loaded (defined in startTimer)
			if (currentTime < startTimer) {
				try {
					Thread.sleep(startTimer - currentTime);
				} catch (InterruptedException e) {
					
				}
			}
			
			// Spawn a new missile every interval of spawnTimer
			if (clock == spawnTimer) {
				clock = 0;	// Reset the clock
				int color = ((int) (Math.random() * 3)) % 3;	// Randomly choose a color tier (0=primary, 1=secondary, 2=tertiary)
				EnemyMissile missile = new EnemyMissile(0, color, resources);	// Generate an enemy missile (must be done once before setting the X location in order to get the width)
				int x = (int) ((Math.random() * (DisplayAdvisor.getX() - missile.missileImage.getWidth())) + (missile.missileImage.getWidth() / 2));	// Get random X location
				missile = new EnemyMissile(x, color, resources);		// Recreate the missile with an X location
				enemyMissiles.add(missile);	// Add the missile to the array list
			}
			
			SurfaceHolder surfaceHolder = getHolder();
			if (surfaceHolder.getSurface().isValid()) {
				Canvas canvas = surfaceHolder.lockCanvas();
				drawEverything(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
			
			// Animate color indicator for cooldown timer
			if (indicatorRadius < indicatorMaxSize) {
				indicatorRadius += indicatorMaxSize / cooldownTimer;
			}
			else {
				indicatorRadius = indicatorMaxSize;
			}
			
			// Loop through player missiles and animate them launching upwards (missiles should accelerate)
			for (int i = 0; i < playerMissiles.size(); i++) {
				PlayerMissile missile = playerMissiles.get(i);
				missile.curY -= missile.speed;
				missile.speed += DisplayAdvisor.getY() / 700;
				if (missile.curY < 0 - (missile.missileImage.getHeight() / 2)) {
					playerMissiles.remove(i);
				}
			}
			
			// Loop through enemy missiles are animate them falling downwards
			for (int i = 0; i < enemyMissiles.size(); i++) {
				EnemyMissile missile = enemyMissiles.get(i);
				missile.curY += fallSpeed;
				
				// If missile hits the ground, explode and subtract health
				if (missile.curY > paletteBar) {
					explosions.add(new Explosion(missile.curX, missile.curY, missile.missileColor));
					enemyMissiles.remove(i);
					health -= 1;
					if (health == 2) {
						hit1.start();
					}
					else if (health == 1) {
						hit2.start();
					}
					else if (health == 0) {
						hit3.start();
					}
					else if (health < 0 && !gameOver) {
						gameOverExplosion = new Explosion(missile.curX, missile.curY, ColorType.BLACK);
						gameOver = true;
						hit4.start();
					}
				}
			}
			
			// Loop through explosions and animate them expanding and fading out
			for (int i = 0; i < explosions.size(); i++) {
				Explosion explosion = explosions.get(i);
				explosion.setPaint();
				if (explosion.opacity <= 0) {
					explosions.remove(i);
				}
				explosion.radius += 10;
				explosion.opacity -= 10;
			}
			
			// Loop through player missiles and check their distance from each enemy missile
			// If a collision has occurred, check color logic to determine whether hit was a success
			// If it was successful, explode both missiles
			// If unsuccessful, only explode player missile
			for (int i = 0; i < playerMissiles.size(); i++) {
				PlayerMissile playerMissile = playerMissiles.get(i);
				for (int j = 0; j < enemyMissiles.size(); j++) {
					EnemyMissile enemyMissile = enemyMissiles.get(j);
					int distance = (int) Math.sqrt(Math.pow(playerMissile.curX - enemyMissile.curX, 2) + Math.pow(playerMissile.curY - enemyMissile.curY, 2));
					if (distance <= 80) {
						boolean success;
						if (complement) {
							success = collideComplement(playerMissile.missileColor, enemyMissile.missileColor);
						}
						else {
							success = collideMatch(playerMissile.missileColor, enemyMissile.missileColor);
						}
						
						explosions.add(new Explosion(playerMissile.curX, playerMissile.curY, playerMissile.missileColor));
						playerMissiles.remove(i);
						
						if (success) {
							explosions.add(new Explosion(enemyMissile.curX, enemyMissile.curY, enemyMissile.missileColor));
							enemyMissiles.remove(j);
							
							score += pointValue;

							destroy_incoming.seekTo(0);
							destroy_incoming.start();
						}
						else {
							fail_incoming.seekTo(0);
							fail_incoming.start();
						}
					}
				}
			}
			
			// Animate health bar depending on current health
			if (health == 2) {
				if (healthBarWidth > healthBar2) {
					healthBarWidth -= DisplayAdvisor.getX() / 100;
				}
				else {
					healthBarWidth = healthBar2;
				}
			}
			else if (health == 1) {
				if (healthBarWidth > healthBar1) {
					healthBarWidth -= DisplayAdvisor.getX() / 100;
				}
				else {
					healthBarWidth = healthBar1;
				}
			}
			else if (health == 0) {
				if (healthBarWidth > 0) {
					healthBarWidth -= DisplayAdvisor.getX() / 100;
				}
				else {
					healthBarWidth = 0;
				}
			}
			
			if (gameOver) {
				gameOverExplosion.radius += 50;
				if (gameOverExplosion.radius > 5000) {
					endGame();
				}
			}
			
			doButtonLogic();	// Check whether player has tapped a button
			clock++;	// Increment the clock
		}
	}

	// Game over, triggered by health < 0 or tapping the Quit button
	private void endGame() {
		intro.stop();
		Intent myIntent = new Intent(this.activity, GameOverActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		myIntent.putExtra("score", score);
		this.activity.startActivity(myIntent);
		this.activity.finish();
		this.activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	// Fire a player missile of the current indicator color, triggered by tapping the screen
	private void fireMissile(int x) {
		PlayerMissile missile = new PlayerMissile(x, indicator, resources);
		playerMissiles.add(missile);
		
		indicatorRadius = 0;
		indicatorPaint = white;
		indicator = ColorType.WHITE;
		palette.r = 0;
		palette.y = 0;
		palette.b = 0;
		palette.calculateColors();
	}

	// Check whether the player has tapped in between four points
	private boolean checkSquareTouchLocation(Point point, Bitmap element, int elementStartX, int elementStartY) {
		if (point.x > elementStartX && point.x < elementStartX + element.getWidth() && point.y > elementStartY && point.y < elementStartY + element.getHeight()) {
			return true;
		}
		return false;
	}

	// Check whether the player has tapped within the radius of a circular button
	private boolean checkCircleTouchLocation(Point point, int elementX, int elementY, int radius) {
		int distance = (int) Math.sqrt(Math.pow(point.x - elementX, 2) + Math.pow(point.y - elementY, 2));
		if (distance <= radius) {
			return true;
		}
		return false;
	}

	// Draw all image assets
	private void drawEverything(Canvas canvas) {
		// Background
		if (health == 3) {
			canvas.drawBitmap(background0, (DisplayAdvisor.getX()/2 - background0.getWidth()/2), 0, null);
		}
		else if (health == 2) {
			canvas.drawBitmap(background1, (DisplayAdvisor.getX()/2 - background1.getWidth()/2), 0, null);
		}
		else if (health == 1) {
			canvas.drawBitmap(background2, (DisplayAdvisor.getX()/2 - background2.getWidth()/2), 0, null);
		}
		else {
			canvas.drawBitmap(background3, (DisplayAdvisor.getX()/2 - background3.getWidth()/2), 0, null);
		}
		
		// Buttons
		canvas.drawBitmap(btnQuit, btnQuitX, btnQuitY, null);

		// Missiles
		for (int i = 0; i < playerMissiles.size(); i++) {
			PlayerMissile missile = playerMissiles.get(i);
			canvas.drawBitmap(missile.missileImage, missile.curX - (missile.missileImage.getWidth() / 2), missile.curY - (missile.missileImage.getHeight() / 2), null);
		}

		for (int i = 0; i < enemyMissiles.size(); i++) {
			EnemyMissile missile = enemyMissiles.get(i);
			canvas.drawBitmap(missile.missileImage, missile.curX - (missile.missileImage.getWidth() / 2), missile.curY - (missile.missileImage.getHeight() / 2), null);
		}
		
		// UI
		Paint healthBarColor = new Paint();
		healthBarColor.setARGB(255, 0, 190, 0);
		canvas.drawRect(0, paletteBar, DisplayAdvisor.getX(), DisplayAdvisor.getY(), black);
		canvas.drawCircle(indicatorX, indicatorY, indicatorMaxSize + (indicatorMaxSize / 6), black);
		canvas.drawRect(0, DisplayAdvisor.getY() - (2 * paletteRadius) - (2 * paletteRadius / 3), healthBarWidth, DisplayAdvisor.getY() - (paletteRadius / 3), healthBarColor);

		Paint highlightDim = new Paint();
		Paint highlightFull = new Paint();

		highlightDim.setARGB(120, 255, 255, 255);
		highlightFull.setARGB(140, 255, 255, 255);
		
		// Palette button highlighting
		if (palette.r == 1) {
			canvas.drawCircle(btnRedX, btnRedY, paletteRadius + (paletteRadius / 3), highlightDim);
		}
		else if (palette.r == 2) {
			canvas.drawCircle(btnRedX, btnRedY, paletteRadius + (paletteRadius / 3), highlightFull);
			canvas.drawCircle(btnRedX, btnRedY, paletteRadius + (paletteRadius / 2), highlightDim);
		}
		if (palette.y == 1) {
			canvas.drawCircle(btnYellowX, btnYellowY, paletteRadius + (paletteRadius / 3), highlightDim);
		}
		else if (palette.y == 2) {
			canvas.drawCircle(btnYellowX, btnYellowY, paletteRadius + (paletteRadius / 3), highlightFull);
			canvas.drawCircle(btnYellowX, btnYellowY, paletteRadius + (paletteRadius / 2), highlightDim);
		}
		if (palette.b == 1) {
			canvas.drawCircle(btnBlueX, btnBlueY, paletteRadius + (paletteRadius / 3), highlightDim);
		}
		else if (palette.b == 2) {
			canvas.drawCircle(btnBlueX, btnBlueY, paletteRadius + (paletteRadius / 3), highlightFull);
			canvas.drawCircle(btnBlueX, btnBlueY, paletteRadius + (paletteRadius / 2), highlightDim);
		}
		
		// Palette buttons
		canvas.drawCircle(btnRedX, btnRedY, paletteRadius, red);
		canvas.drawCircle(btnYellowX, btnYellowY, paletteRadius, yellow);
		canvas.drawCircle(btnBlueX, btnBlueY, paletteRadius, blue);
		canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, indicatorPaint);
		
		// Explosions
		for (int i = 0; i < explosions.size(); i++) {
			Explosion explosion = explosions.get(i);
			canvas.drawCircle(explosion.x, explosion.y, explosion.radius, explosion.paint);
		}
		
		// Score
		Paint scoreText = white;
		scoreText.setTextSize(DisplayAdvisor.getY() / 15); 
		canvas.drawText(Integer.toString(score), (DisplayAdvisor.getX() / 2) - (scoreText.measureText(Integer.toString(score)) / 2), 7 * DisplayAdvisor.getY() / 8, scoreText); 
		
		// Game Over Animation
		canvas.drawCircle(gameOverExplosion.x, gameOverExplosion.y, gameOverExplosion.radius, black);
	}

	// Complement color logic
	private boolean collideComplement(ColorType player, ColorType enemy) {
		if (player == ColorType.RED && enemy == ColorType.GREEN) {
			return true;
		}
		if (player == ColorType.SCARLET && enemy == ColorType.TURQUOISE) {
			return true;
		}
		if (player == ColorType.ORANGE && enemy == ColorType.BLUE) {
			return true;
		}
		if (player == ColorType.VERMILION && enemy == ColorType.INDIGO) {
			return true;
		}
		if (player == ColorType.YELLOW && enemy == ColorType.PURPLE) {
			return true;
		}
		if (player == ColorType.CHARTREUSE && enemy == ColorType.FUCHSIA) {
			return true;
		}
		if (player == ColorType.GREEN && enemy == ColorType.RED) {
			return true;
		}
		if (player == ColorType.TURQUOISE && enemy == ColorType.SCARLET) {
			return true;
		}
		if (player == ColorType.BLUE && enemy == ColorType.ORANGE) {
			return true;
		}
		if (player == ColorType.INDIGO && enemy == ColorType.VERMILION) {
			return true;
		}
		if (player == ColorType.PURPLE && enemy == ColorType.YELLOW) {
			return true;
		}
		if (player == ColorType.FUCHSIA && enemy == ColorType.CHARTREUSE) {
			return true;
		}
		if (player == ColorType.WHITE && enemy == ColorType.BLACK) {
			return true;
		}
		if (player == ColorType.BLACK && enemy == ColorType.WHITE) {
			return true;
		}
		
		return false;
	}

	// Match color logic
	private boolean collideMatch(ColorType player, ColorType enemy) {
		if (player == enemy) {
			return true;
		}
		
		return false;
	}

	// Perform actions for each respective button
	private void doButtonLogic() {
		if (activity.wasTouched) {
			Point point = activity.getTouch();
			
			// Quit button
			if (checkSquareTouchLocation(point, btnQuit, btnQuitX, btnQuitY)) {
				endGame();
			}
			// Red palette button
			else if (checkCircleTouchLocation(point, btnRedX, btnRedY, paletteRadius)) {
				palette.r += 1;
				if (palette.r > 2) {
					palette.r = 0;
				}
				palette.calculateColors();
				
				indicator = palette.color;
				indicatorPaint = palette.paint;
			}
			// Yellow palette button
			else if (checkCircleTouchLocation(point, btnYellowX, btnYellowY, paletteRadius)) {
				palette.y += 1;
				if (palette.y > 2) {
					palette.y = 0;
				}
				palette.calculateColors();
				
				indicator = palette.color;
				indicatorPaint = palette.paint;
			}
			// Blue palette button
			else if (checkCircleTouchLocation(point, btnBlueX, btnBlueY, 3 * paletteRadius / 2)) {
				palette.b += 1;
				if (palette.b > 2) {
					palette.b = 0;
				}
				palette.calculateColors();
				
				indicator = palette.color;
				indicatorPaint = palette.paint;
			}
			// Screen tap
			else if (point.y < paletteBar){
				if (indicatorRadius == indicatorMaxSize) {
					fireMissile(point.x);
				}
			}
		}
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
