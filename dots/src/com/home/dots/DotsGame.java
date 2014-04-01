package com.home.dots;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.home.dots.screens.StartScreen;

public class DotsGame extends Game {

	public static int WIDTH = 768;
	public static int HEIGHT = 1280;
	
    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }
    
   	public void render() {
   		super.render(); // important!
   	}
    
    @Override
    public void dispose() {
    	super.dispose();
    }
    
    public void newScreen(Screen screen) {
        getScreen().dispose();
        setScreen(screen);
    }
    
}