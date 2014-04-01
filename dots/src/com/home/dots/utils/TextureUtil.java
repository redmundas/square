package com.home.dots.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class TextureUtil {
	
	public static Texture createSquare(Color color) {
		Pixmap pixmap = new Pixmap(2, 2, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 2, 2);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
	
}
