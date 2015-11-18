package com.jimmyswanbeck.colorwars;

import android.graphics.Paint;
import com.jimmyswanbeck.colorwars.Palette.ColorType;

public class Explosion {
	// Screen location
	int x;
	int y;
	
	int radius = 0;		// Explosion size
	int opacity = 255;	// Explosion opacity
	ColorType color;	// Logical color of explosion
	Paint paint;		// Graphical color of explosion
	
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
	
	// Set screen location and logical color of explosion
	public Explosion(int X, int Y, ColorType Color) {
		x = X;
		y = Y;
		color = Color;
		
		setPaint();
	}

	// Set opacity and determine graphical color based on logical color
	private void getPaint() {
		if (color == ColorType.RED) {
			paint = red;
		}
		if (color == ColorType.SCARLET) {
			paint = scarlet;
		}
		if (color == ColorType.ORANGE) {
			paint = orange;
		}
		if (color == ColorType.VERMILION) {
			paint = vermilion;
		}
		if (color == ColorType.YELLOW) {
			paint = yellow;
		}
		if (color == ColorType.CHARTREUSE) {
			paint = chartreuse;
		}
		if (color == ColorType.GREEN) {
			paint = green;
		}
		if (color == ColorType.TURQUOISE) {
			paint = turquoise;
		}
		if (color == ColorType.BLUE) {
			paint = blue;
		}
		if (color == ColorType.INDIGO) {
			paint = indigo;
		}
		if (color == ColorType.PURPLE) {
			paint = purple;
		}
		if (color == ColorType.FUCHSIA) {
			paint = fuchsia;
		}
		if (color == ColorType.WHITE) {
			paint = white;
		}
		if (color == ColorType.BLACK) {
			paint = black;
		}
		
		paint.setAlpha(opacity);
	}

	// Set up paint variables
	public void setPaint() {
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
		
		getPaint();
	}
}
