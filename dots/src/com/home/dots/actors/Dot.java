package com.home.dots.actors;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Dot extends Actor {
	
	private static Map<String, Texture> TEXTURES = new HashMap<String, Texture>();
	
	public static int SIZE = 55;
	
	public int i;
	public int j;
	
	private Texture texture;
	
	public Dot() {
		
	}
	
	public void init(Color color, int i, int j) {
		this.i = i; this.j = j;
		int x = i*(Dot.SIZE + Dot.SIZE);
		int y = j*(Dot.SIZE + Dot.SIZE);
		
		setWidth(SIZE);
		setHeight(SIZE);
		setColor(color);
		setPosition(x, y);
		setBounds(x, y, getWidth(), getHeight());
		
		if(texture != null)
			texture.dispose();
		texture = createSquare(color);
	}
	
	public void pulse() {
		addAction(Actions.sequence(
				Actions.parallel(Actions.sizeTo(SIZE+SIZE/5, SIZE+SIZE/5, 0.15f), Actions.moveBy(-SIZE/5/2, -SIZE/5/2, 0.15f)), 
				Actions.parallel(Actions.sizeTo(SIZE, SIZE, 0.15f), Actions.moveBy(SIZE/5/2, SIZE/5/2, 0.15f))));
	}
	
	public void fixStartPosition(int i, int j) {
		int x = (this.i + i)*(Dot.SIZE + Dot.SIZE);
		int y = (this.j + j)*(Dot.SIZE + Dot.SIZE);
		setPosition(x, y);
	}
	
	public void moveDown() {
		int y = (Dot.SIZE + Dot.SIZE);
		addAction(Actions.sequence(Actions.moveBy(0, -y, 0.15f), Actions.moveBy(0, SIZE/5, 0.1f), Actions.moveBy(0, -SIZE/5, 0.1f)));
	}
	
	public void moveDownBy(int n) {
		int y = (Dot.SIZE + Dot.SIZE) * n;
		addAction(Actions.sequence(Actions.moveBy(0, -y, 0.15f), Actions.moveBy(0, SIZE/5, 0.1f), Actions.moveBy(0, -SIZE/5, 0.1f)));
	}
	
	public void destroy() {
		addAction(Actions.sequence(Actions.parallel(Actions.sizeTo(0, 0, 0.2f), Actions.moveBy(SIZE/2, SIZE/2, 0.2f)), Actions.run(new Runnable() {
			@Override
			public void run() {
				remove();
			}
		})));
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public int hashCode() {
		return i * 10 + j;
	}
	
	private static Texture createSquare(Color color) {
		Pixmap pixmap = new Pixmap(2, 2, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 2, 2);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

}
