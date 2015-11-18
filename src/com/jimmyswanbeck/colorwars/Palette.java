package com.jimmyswanbeck.colorwars;

import android.graphics.Paint;

public class Palette {
	// Logical colors
	public enum ColorType {
		RED,
		SCARLET,
		ORANGE,
		VERMILION,
		YELLOW,
		CHARTREUSE,
		GREEN,
		TURQUOISE,
		BLUE,
		INDIGO,
		PURPLE,
		FUCHSIA,
		WHITE,
		BLACK
	}
	
	// Current palette values
	int r = 0;
	int y = 0;
	int b = 0;
	ColorType color;
	Paint paint;
    
	// Standardized paint values
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
	
	// Determine indicator color based on palette values
	public void calculateColors() {
		if (r == 0 && y == 0 && b == 0) {
			color = ColorType.WHITE;
			paint = white;
		}
		else if ((r == 1 && y == 0 && b == 0) || (r == 2 && y == 0 && b == 0)) {
			color = ColorType.RED;
			paint = red;
		}
		else if (r == 2 && y == 1 && b == 0) {
			color = ColorType.SCARLET;
			paint = scarlet;
		}
		else if ((r == 1 && y == 1 && b == 0) || (r == 2 && y == 2 && b == 0)) {
			color = ColorType.ORANGE;
			paint = orange;
		}
		else if (r == 1 && y == 2 && b == 0) {
			color = ColorType.VERMILION;
			paint = vermilion;
		}
		else if ((r == 0 && y == 1 && b == 0) || (r == 0 && y == 2 && b == 0)) {
			color = ColorType.YELLOW;
			paint = yellow;
		}
		else if (r == 0 && y == 2 && b == 1) {
			color = ColorType.CHARTREUSE;
			paint = chartreuse;
		}
		else if ((r == 0 && y == 1 && b == 1) || (r == 0 && y == 2 && b == 2)) {
			color = ColorType.GREEN;
			paint = green;
		}
		else if (r == 0 && y == 1 && b == 2) {
			color = ColorType.TURQUOISE;
			paint = turquoise;
		}
		else if ((r == 0 && y == 0 && b == 1) || (r == 0 && y == 0 && b == 2)) {
			color = ColorType.BLUE;
			paint = blue;
		}
		else if (r == 1 && y == 0 && b == 2) {
			color = ColorType.INDIGO;
			paint = indigo;
		}
		else if ((r == 1 && y == 0 && b == 1) || (r == 2 && y == 0 && b == 2)) {
			color = ColorType.PURPLE;
			paint = purple;
		}
		else if (r == 2 && y == 0 && b == 1) {
			color = ColorType.FUCHSIA;
			paint = fuchsia;
		}
		else if ((r == 1 && y == 1 && b == 1) || (r == 2 && y == 1 && b == 1) || (r == 2 && y == 2 && b == 1) || (r == 1 && y == 2 && b == 1) || (r == 1 && y == 2 && b == 2) || (r == 1 && y == 1 && b == 2) || (r == 2 && y == 1 && b == 2) || (r == 2 && y == 2 && b == 2)) {
			color = ColorType.BLACK;
			paint = black;
		}
	}
	
	// Define standardized paint colors
	public void setColors() {
		red.setARGB(255, 237, 36, 35);
		scarlet.setARGB(255, 241, 90, 42);
		orange.setARGB(255, 247, 147, 28);
		vermilion.setARGB(255, 251, 175, 86);
		yellow.setARGB(255, 255, 242, 0);
		chartreuse.setARGB(255, 139, 198, 63);
		green.setARGB(255, 0, 166, 81);
		turquoise.setARGB(255, 18, 137, 126);
		blue.setARGB(255, 62, 65, 185);
		indigo.setARGB(255, 102, 45, 144);
		purple.setARGB(255, 145, 40, 144);
		fuchsia.setARGB(255, 217, 36, 92);
		white.setARGB(255, 255, 255, 255);
		black.setARGB(255, 0, 0, 0);
	}
}
