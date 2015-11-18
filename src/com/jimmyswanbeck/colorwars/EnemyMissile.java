package com.jimmyswanbeck.colorwars;

import com.jimmyswanbeck.colorwars.Palette.ColorType;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;

public class EnemyMissile {
	ColorType missileColor;		// Logical color of missile
	Bitmap missileImage = null;	// Missile image
	
	// Screen location
	int curX = 0;
	int curY = 0;
	
	// Ideal width/height ratios for missile images to be scaled by
	int primaryIdealWidth = 101;
	int primaryIdealHeight = 215;
	int secondaryIdealWidth = 92;
	int secondaryIdealHeight = 224;
	int tertiaryIdealWidth = 143;
	int tertiaryIdealHeight = 192;
    
	// Paint variables (to be set by palette)
	Palette palette = new Palette();
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
	
	public EnemyMissile(int x, int color, Resources resources) {
		// Set all paint colors to those defined in Palette class
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
		
		if (color == 0) {
			color = ((int) (Math.random() * 5)) % 5;
		}
		else if (color == 1) {
			color = (((int) (Math.random() * 5)) % 3) + 5;
		}
		else {
			color = (((int) (Math.random() * 6)) % 6) + 8;
		}
		
		// Set bitmap image and logical color (based on color variable from method argument)
		if (color == 0) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, primaryIdealWidth, primaryIdealHeight, R.drawable.missile_red);
			missileColor = ColorType.RED;
		}
		if (color == 8) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_scarlet);
			missileColor = ColorType.SCARLET;
		}
		if (color == 5) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, secondaryIdealWidth, secondaryIdealHeight, R.drawable.missile_orange);
			missileColor = ColorType.ORANGE;
		}
		if (color == 9) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_vermilion);
			missileColor = ColorType.VERMILION;
		}
		if (color == 1) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, primaryIdealWidth, primaryIdealHeight, R.drawable.missile_yellow);
			missileColor = ColorType.YELLOW;
		}
		if (color == 10) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_chartreuse);
			missileColor = ColorType.CHARTREUSE;
		}
		if (color == 6) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, secondaryIdealWidth, secondaryIdealHeight, R.drawable.missile_green);
			missileColor = ColorType.GREEN;
		}
		if (color == 11) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_turquoise);
			missileColor = ColorType.TURQUOISE;
		}
		if (color == 2) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, primaryIdealWidth, primaryIdealHeight, R.drawable.missile_blue);
			missileColor = ColorType.BLUE;
		}
		if (color == 12) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_indigo);
			missileColor = ColorType.INDIGO;
		}
		if (color == 7) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, secondaryIdealWidth, secondaryIdealHeight, R.drawable.missile_purple);
			missileColor = ColorType.PURPLE;
		}
		if (color == 13) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, tertiaryIdealWidth, tertiaryIdealHeight, R.drawable.missile_fuchsia);
			missileColor = ColorType.FUCHSIA;
		}
		if (color == 3) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, primaryIdealWidth, primaryIdealHeight, R.drawable.missile_white);
			missileColor = ColorType.WHITE;
		}
		if (color == 4) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, primaryIdealWidth, primaryIdealHeight, R.drawable.missile_black);
			missileColor = ColorType.BLACK;
		}
		
		// Set initial missile location (just above the top of the screen)
		curX = x;
		curY = 0 - (missileImage.getHeight() / 2);
	}		
}
