package com.home.dots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.home.dots.DotsGame;

public class ScoreScreen extends CommonScreen implements Screen {

    private final DotsGame game;
	private final int score;
	private Stage stage;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	private Texture texture;
	private BitmapFont font1;
	private BitmapFont font2;
	
    
    public ScoreScreen(final DotsGame game, int score) {
        this.game = game;
        this.score = score;
        
		stage = new Stage();
//		Gdx.input.setInputProcessor(stage);
    	
		font1 = new BitmapFont(Gdx.files.internal("ocr_a.fnt"), false);
		font1.setColor(Color.WHITE);
		font1.scale(1f);
		font2 = new BitmapFont(Gdx.files.internal("ocr_a.fnt"), false);
		font2.setColor(Color.WHITE);
		font2.scale(2f);
		
		Label labelText = new Label("Your score", new Label.LabelStyle(font1, Color.WHITE));
		labelText.setAlignment(Align.center);
		labelText.setSize(WIDTH, 100);
		labelText.setPosition(0, HEIGHT-450);
		
		Label labelScore = new Label(String.valueOf(score), new Label.LabelStyle(font2, Color.WHITE));
		labelScore.setAlignment(Align.center);
		labelScore.setSize(WIDTH, 200);
		labelScore.setPosition(0, HEIGHT-600);

		stage.addActor(labelText);
		stage.addActor(labelScore);
    }
    
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.GRAY.r+0.1f, Color.GRAY.g+0.1f, Color.GRAY.b+0.1f, Color.GRAY.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.getCamera().update();
        
		stage.act(delta);
		stage.draw();
		
		if(Gdx.input.isTouched()) {
			game.newScreen(new StartScreen(game));
		}
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
