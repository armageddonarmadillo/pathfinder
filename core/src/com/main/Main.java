package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	static Tile last_tile = null;
	static Random r = new Random();
	int counter = 0;
	Point start = new Point(0, r.nextInt(10));
	Point end = new Point(9, r.nextInt(10));

	ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
	@Override
	public void create () {
		batch = new SpriteBatch();
		for(int x = 0; x < 10; x++) {
			ArrayList<Tile> temp_tiles = new ArrayList<Tile>();
			for(int y = 0; y < 10; y++)
				temp_tiles.add(new Tile(x, y));
			tiles.add(temp_tiles);
		}
		occupy_tile(end.x, end.y);
		occupy_tile(start.x, start.y);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1f, 0.6f, 0.8f, 1);
		update();
		batch.begin();
		batch.draw(res.grid, 0, 0);
		draw_tiles();
		batch.end();
	}

	void update(){
		move(last_tile);
		tap();
	}

	void tap(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX() / res.cw, y = (Gdx.graphics.getHeight() - Gdx.input.getY()) / res.ch;
			System.out.println("X: " + x + ", Y: " + y);
		}
	}

	void draw_tiles(){
		for(int x = 0; x < tiles.size(); x++)
			for(int y = 0; y < tiles.get(x).size(); y++)
				tiles.get(x).get(y).draw(batch);
	}

	Point find_path(Tile c){
		return new Point(c.x + 50, c.y);
	}

	void locate_next_tile(Tile n){
		if(n == null) return;
		for(int r = 0; r < tiles.size(); r++)
			for(int c = 0; c < tiles.get(r).size(); c++)
				if(!tiles.get(r).get(c).occupied &&
						(n.x + 1 == tiles.get(r).get(c).x)) {
					tiles.get(r).get(c).occupy();
					last_tile = tiles.get(r).get(c);
					return;
				}
	}

	void move(Tile n){
		if(n == null && counter < 1) return;
		for(int r = 0; r < tiles.size(); r++)
			for(int c = 0; c < tiles.get(r).size(); c++){
				Tile temp_tile = tiles.get(r).get(c);
				//move from current tile towards end
				//idea: calc difference in y position to determine whetehr to go up or down
				if(n.x == temp_tile.x && n.y == temp_tile.y)
					if(end.y - temp_tile.y != 0 && (end.y - temp_tile.y) / Math.abs(end.y - temp_tile.y) > 0)
						occupy_tile(n.x + 1, n.y + 1);
					else if (end.y - temp_tile.y != 0 && (end.y - temp_tile.y) / Math.abs(end.y - temp_tile.y) < 0)
						occupy_tile(n.x + 1, n.y - 1);
					else
						occupy_tile(n.x + 1, n.y);
			}
	}

	void link_tiles(Tile last, Tile current){
		last.next = current;
		current.previous = last;
	}

	void occupy_tile(int x, int y){
		for(int r = 0; r < tiles.size(); r++)
			for(int c = 0; c < tiles.get(r).size(); c++)
				if(tiles.get(r).get(c).x == x && tiles.get(r).get(c).y == y){
					if(counter++ > 1) link_tiles(last_tile, tiles.get(r).get(c));
					tiles.get(r).get(c).occupy();
					last_tile = tiles.get(r).get(c);
				}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
