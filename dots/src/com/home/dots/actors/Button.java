package com.home.dots.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.home.dots.utils.TextureUtil;

public class Button extends Table {
	
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

	private final String text;
	private Label label;
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 100;
	
	public Button(String text, Skin skin) {
		this.text = text;
		
		setSize(WIDTH, HEIGHT);
		
		label = new Label(text, skin);
		label.setFontScale(2);
		label.setAlignment(Align.center);
		label.setSize(WIDTH, HEIGHT);
		
		addActor(label);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
