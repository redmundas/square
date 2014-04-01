package com.home.dots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.home.dots.DotsGame;
import com.home.dots.actors.Button;
import com.home.dots.actors.GameEngine;

public class StartScreen extends CommonScreen implements Screen {

    private final DotsGame game;
	private Stage stage;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public StartScreen(final DotsGame game) {
        this.game = game;
        
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
        
        Button btn1 = new Button("Moves", skin);
        Button btn2 = new Button("Timed", skin);
        Button btn3 = new Button("Endless", skin);
        
        btn1.setX((WIDTH - btn1.getWidth()) / 2);
        btn1.setY(HEIGHT - btn1.getHeight() - 350);
        btn2.setX((WIDTH - btn2.getWidth()) / 2);
        btn2.setY(HEIGHT - btn2.getHeight() - 550);
        btn3.setX((WIDTH - btn3.getWidth()) / 2);
        btn3.setY(HEIGHT - btn3.getHeight() - 750);
        
        btn1.addListener(new InputListener() {
        	@Override
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        	@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	showGameScreen(GameEngine.Mode.MOVES);
            }
        });
        
        btn2.addListener(new InputListener() {
        	@Override
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        	@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	showGameScreen(GameEngine.Mode.TIMED);
            }
        });
        
        btn3.addListener(new InputListener() {
        	@Override
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        	@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	showGameScreen(GameEngine.Mode.ENDLESS);
            }
        });

		stage.addActor(btn1);
		stage.addActor(btn2);
		stage.addActor(btn3);
    }
    
    public void showGameScreen(GameEngine.Mode mode) {
    	game.newScreen(new GameScreen(game, mode));
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.LIGHT_GRAY.r+0.1f, Color.LIGHT_GRAY.g+0.1f, Color.LIGHT_GRAY.b+0.1f, Color.LIGHT_GRAY.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.getCamera().update();
        
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