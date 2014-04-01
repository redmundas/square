package com.home.dots.input;

import com.badlogic.gdx.InputProcessor;
import com.home.dots.actors.GameEngine;

public class DotInputProcessor implements InputProcessor {
	
	private GameEngine engine;
	
	public DotInputProcessor(GameEngine engine) {
		this.engine = engine;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		engine.handleRelease();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		engine.handleDrag(screenX, screenY);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
