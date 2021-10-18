package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;

public class Tile {
    int x, y, w = 50, h = 50;
    boolean occupied = false;
    Tile previous = null, next = null;

    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    Tile(int x, int y, Tile prev, Tile next){
        this.x = x;
        this.y = y;
        previous = prev;
        this.next = next;
    }

    Tile(int x, int y, Tile tile, boolean is_next){
        this.x = x;
        this.y = y;
        previous = !is_next ? tile : null;
        next = is_next ? tile : null;
    }

    void occupy(){ occupied = true; }

    void draw(SpriteBatch b){
        if(occupied) b.draw(res.tex, x * res.cw, y * res.ch);
    }
}
