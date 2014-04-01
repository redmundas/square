package com.home.dots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.home.dots.DotsGame;
import com.home.dots.actors.GameEngine;
import com.home.dots.actors.Status;

public class GameScreen extends CommonScreen implements Screen {

	private final DotsGame game;
	private Stage stage;
	private GameEngine engine;
	private Status score;
	
	public GameScreen(DotsGame game, GameEngine.Mode mode) {
		this.game = game;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		engine = new GameEngine(game, mode, stage.getCamera());
		score = new Status(mode, WIDTH, HEIGHT);
		stage.addActor(engine);
		stage.addActor(score);
		
		engine.setX((WIDTH - engine.getWidth()) / 2);
		engine.setY((HEIGHT - engine.getHeight()) / 2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.getCamera().update();
		
		score.setScore(engine.score());
		score.setMoves(engine.moves());
		score.setTime(engine.time());
        
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(WIDTH, HEIGHT, true);
		stage.getCamera().translate(-stage.getGutterWidth(),
		    -stage.getGutterHeight(), 0);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		stage.clear();
	}

}
