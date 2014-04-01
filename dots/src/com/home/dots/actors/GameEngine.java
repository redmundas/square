package com.home.dots.actors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.home.dots.DotsGame;
import com.home.dots.input.DotInputProcessor;
import com.home.dots.input.StageInputProcessor;
import com.home.dots.screens.GameScreen;
import com.home.dots.screens.ScoreScreen;

public class GameEngine extends Table {
	
	private static int MOVES = 30;
	private static int TIME = 60;
	private static int SIZE = 6;
	private static int WIDTH = Dot.SIZE/5;

	private final DotsGame game;
	
	private int restore = 0;
	
	private Dot[][] grid = new Dot[SIZE][SIZE];
	private Set<Color> colors = new HashSet<Color>();
	private List<Dot> bucket = new LinkedList<Dot>();
	
	private Camera camera;
	
	private int points = 0;
	private int moves = MOVES;
	private int time = TIME;
	private long timeStart = System.currentTimeMillis();
	
	private Mode mode;
	private boolean enabled = true;
	
	private Color lastColor;
	private Dot currentDot;
	private Vector2 position;
	
	private DotsPool pool = new DotsPool(SIZE*SIZE*2);
	
	public GameEngine(final DotsGame game, Mode mode, Camera camera) {
		this.game = game;
		this.camera = camera;
		this.mode = mode;
		
		colors.add(Color.PINK);
		colors.add(Color.CYAN);
		colors.add(Color.MAGENTA);
		colors.add(Color.ORANGE);
		colors.add(Color.GREEN);
		
		Gdx.gl.glLineWidth(WIDTH);
		
		setWidth(Dot.SIZE*(SIZE*2-1));
		setHeight(Dot.SIZE*(SIZE*2-1));
		setBounds(0, 0, getWidth(), getHeight());
		setClip(false);
		
		gridCreate();
		inputHandle();
	}
	
	private void inputHandle() {
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new DotInputProcessor(this));
//		multiplexer.addProcessor(new StageInputProcessor(this));
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	private void gridCreate() {
		Color[] tColors = colors.toArray(new Color[colors.size()]);
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				int n = MathUtils.random(0, tColors.length - 1);
				grid[i][j] = pool.borrowDot();
				grid[i][j].init(tColors[n], i, j);
				addActor(grid[i][j]);
			}
		}
	}
	
	public void removeDot(Dot dot) {
		if(bucket.size() > 1 && bucket.get(bucket.size()-2).equals(dot))
			bucket.remove(bucket.size()-1);
		else if(!bucket.contains(dot)) {
			bucket.add(dot);
			dot.pulse();
		}
	}
	
	public boolean isAcceptable(Dot dot) {
		if(bucket.size() < 1)
			return true;
		return matchColor(dot) && areNeigboors(bucket.get(bucket.size()-1), dot);
	}
	
	public boolean matchColor(Dot dot) {
		if(bucket.size() < 1)
			return true;
		return bucket.get(bucket.size()-1).getColor().equals(dot.getColor());
	}
	
	public boolean areNeigboors(Dot dot1, Dot dot2) {
		return 
				(dot1.i == dot2.i && (dot1.j-1 == dot2.j || dot1.j+1 == dot2.j))
				|| (dot1.j == dot2.j && (dot1.i-1 == dot2.i || dot1.i+1 == dot2.i));
	}
	
	public void handleDrag(int x, int y) {
		if(enabled) {
			position = screenToLocalCoordinates(new Vector2(x, y));
			Dot dot = findTargetDot(position.x, position.y);
			if(dot != null) {
				if(matchColor(dot)) {
					currentDot = dot;
				}
				if(isAcceptable(dot)) {
					removeDot(dot);
				}
			}
		}
	}
	
	public void handleRelease() {
		if(!enabled)
			return;
		
		lastColor = null;
		if(bucket.size() < 2) {
			if(bucket.size() > 0) {
				bucket.remove(0);
			}
		} else {
			if(bucket.get(0).equals(currentDot)) {
				lastColor = bucket.get(0).getColor();
				for(int i = 0; i < grid.length; i++) {
					for(int j = 0; j < grid[i].length; j++) {
						if(grid[i][j].getColor().equals(bucket.get(0).getColor())
								&& !bucket.contains(grid[i][j])) {
							bucket.add(grid[i][j]);
						}
					}
				}
			}
			for(Dot dot : bucket) {
				dot.clear();
				dot.destroy();
				pool.returnDot(dot);
				grid[dot.i][dot.j] = null;
			}
			points += bucket.size();
			restore = 1;
			handleMove();
		}
		bucket = new LinkedList<Dot>();
	}
	
	private void handleMove() {
		if(mode == Mode.MOVES && enabled) {
			moves--;
			
			if(moves <= 0) {
				enabled = false;
			}
		}
	}
	
	private void handleTime() {
		if(mode == Mode.TIMED && enabled) {
			int diff = (int)((System.currentTimeMillis() - timeStart) / 1000);
			time = TIME - diff;
			
			if(time <= 0) {
				enabled = false;
			}
		}
	}
	
	private Dot findTargetDot(float x, float y) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(x >= grid[i][j].getX() && x <= grid[i][j].getRight()
						&& y >= grid[i][j].getY() && y <= grid[i][j].getTop()) {
					return grid[i][j];
				}
			}
		}
		return null;
	}
	
	private void gridRestore(Map<Integer, Integer> stat) {
		Color[] tColors = colors.toArray(new Color[colors.size()]);
		if(lastColor != null) {
			tColors = new Color[colors.size()-1];
			int n = 0;
			for(Color color : colors) {
				if(!color.equals(lastColor)) {
					tColors[n] = color;
					n++;
				}
			}
		}
		for(int i : stat.keySet()) {
			for(int n = stat.get(i); n > 0; n--) {
				int c = MathUtils.random(0, tColors.length - 1);
				grid[i][SIZE-n] = pool.borrowDot();
				grid[i][SIZE-n].init(tColors[c], i, (SIZE-n));
				grid[i][SIZE-n].fixStartPosition(0, stat.get(i));
				addActor(grid[i][SIZE-n]);
				grid[i][SIZE-n].moveDownBy(stat.get(i));
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(bucket.size() > 1) {
			batch.end();
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			camera.update();
			shapeRenderer.setProjectionMatrix(camera.combined);
			 
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(bucket.get(0).getColor());
			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;
			for(int i = 1; i < bucket.size(); i++) {
				x1 = (int) (bucket.get(i-1).getX() + bucket.get(i-1).getWidth() / 2) + (int) getX();
				y1 = (int) (bucket.get(i-1).getY() + bucket.get(i-1).getHeight() / 2) + (int) getY();
				x2 = (int) (bucket.get(i).getX() + bucket.get(i).getWidth() / 2) + (int) getX();
				y2 = (int) (bucket.get(i).getY() + bucket.get(i).getHeight() / 2) + (int) getY();
				shapeRenderer.line(x1, y1, x2, y2);
			}
			if(bucket.get(0).equals(currentDot)) {
				x1 = x2; y1 = y2;
				x2 = (int) (bucket.get(0).getX() + bucket.get(0).getWidth() / 2) + (int) getX();
				y2 = (int) (bucket.get(0).getY() + bucket.get(0).getHeight() / 2) + (int) getY();
				shapeRenderer.line(x1, y1, x2, y2);
			} else {
				x1 = x2; y1 = y2;
				x2 = (int) (position.x + getX());
				y2 = (int) (position.y + getY());
				shapeRenderer.line(x1, y1, x2, y2);
			}
			shapeRenderer.end();
			batch.begin();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		handleTime();
		if(restore > 5) {
			restore = 0;
			Map<Integer, Integer> stat = new HashMap<Integer, Integer>();
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					if(grid[i][j] == null) {
						int count = 1;
						if(stat.containsKey(i))
							count = stat.get(i) + 1;
						stat.put(i, count);
					}
				}
			}
			for(int i : stat.keySet()) {
				for(int n = stat.get(i); n > 0; n--) {
					for(int j = 1; j < grid[i].length; j++) {
						if(grid[i][j] != null && grid[i][j-1] == null) {
							grid[i][j].j = j-1;
							grid[i][j].moveDown();
							grid[i][j-1] = grid[i][j];
							grid[i][j] = null;
						}
					}
				}
			}
			gridRestore(stat);
		} else if(restore > 0)
			restore++;
		
		handleFinish();
	}
	
	public void dispose() {
		clear();
	}
	
	private void handleFinish() {
		if(enabled)
			return;
		
		game.newScreen(new ScoreScreen(game, points));
        dispose();
	}
	
	public int score() {
		return points;
	}
	
	public int moves() {
		return moves;
	}
	
	public int time() {
		return time;
	}
	
	public enum Mode {
		TIMED, MOVES, ENDLESS
	}

}
