package com.home.dots;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "dots";
		cfg.useGL20 = true;
		cfg.width = 768/2;
		cfg.height = 1280/2;
		
		new LwjglApplication(new DotsGame(), cfg);
	}
}
