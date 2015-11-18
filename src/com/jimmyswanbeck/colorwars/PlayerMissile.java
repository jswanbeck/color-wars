package com.jimmyswanbeck.colorwars;

import com.jimmyswanbeck.colorwars.Palette.ColorType;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;

public class PlayerMissile {
	ColorType missileColor;	// Logical color of missile
	Bitmap missileImage = null;	// Missile image
	
	Palette palette = new Palette();
	
	// Screen location
	int curX = 0;
	int curY = 0;
	
	int speed = 1;	// Missile speed (determines acceleration)
	
	// Ideal size
	int idealWidth = 93;
	int idealHeight = 262;
    
	// Paint variables (set by palette)
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
	
	public PlayerMissile(int x, ColorType color, Resources resources) {
		// Get values from method arguments
		missileColor = color;
		curX = x;
		curY = DisplayAdvisor.getY();
		
		// Set all paint colors to those defined in the Palette class
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
		
		// Determine missile image based on logical color
		if (missileColor == ColorType.RED) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_red);
		}
		if (missileColor == ColorType.SCARLET) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_scarlet);
		}
		if (missileColor == ColorType.ORANGE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_orange);
		}
		if (missileColor == ColorType.VERMILION) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_vermilion);
		}
		if (missileColor == ColorType.YELLOW) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_yellow);
		}
		if (missileColor == ColorType.CHARTREUSE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_chartreuse);
		}
		if (missileColor == ColorType.GREEN) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_green);
		}
		if (missileColor == ColorType.TURQUOISE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_turquoise);
		}
		if (missileColor == ColorType.BLUE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_blue);
		}
		if (missileColor == ColorType.INDIGO) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_indigo);
		}
		if (missileColor == ColorType.PURPLE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_purple);
		}
		if (missileColor == ColorType.FUCHSIA) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_fuchsia);
		}
		if (missileColor == ColorType.WHITE) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_white);
		}
		if (missileColor == ColorType.BLACK) {
			missileImage = DisplayAdvisor.loadScaledToIdeal(resources, idealWidth, idealHeight, R.drawable.player_black);
		}
	}		
}
