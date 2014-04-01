package com.home.dots.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.home.dots.screens.GameScreen;
import com.home.dots.utils.TextureUtil;

public class Status extends Table {

	private Texture texture;
	private BitmapFont font1;
	private BitmapFont font2;

	private static final int HEIGHT = 100;
	
	private int score = 0;
	private int moves = 0;
	private int time = 0;
	private GameEngine.Mode mode;

	public Status(GameEngine.Mode mode, int width, int height) {
		this.mode = mode;
		
		setWidth(width);
		setHeight(HEIGHT);
		setPosition(0, height-HEIGHT);
		setBounds(0, height-HEIGHT, width, HEIGHT);
		
		texture = TextureUtil.createSquare(Color.GRAY);
		
		font1 = new BitmapFont(Gdx.files.internal("ocr_a.fnt"), false);
		font1.setColor(Color.WHITE);
		font1.scale(1.0f / 1);
		font2 = new BitmapFont(Gdx.files.internal("ocr_a.fnt"), false);
		font2.setColor(Color.WHITE);
		font2.scale(1.0f / 12);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
		font1.draw(batch, String.valueOf(score), getX()+150, getY()+75);
		font2.draw(batch, "Score", getX()+30, getY()+65);
		if(mode == GameEngine.Mode.MOVES) {
			font1.draw(batch, String.valueOf(moves), getRight()-100, getY()+75);
			font2.draw(batch, "Moves", getRight()-220, getY()+65);
		} else if(mode == GameEngine.Mode.TIMED) {
			font1.draw(batch, String.valueOf(time), getRight()-100, getY()+75);
			font2.draw(batch, "Time", getRight()-210, getY()+65);
		}
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
