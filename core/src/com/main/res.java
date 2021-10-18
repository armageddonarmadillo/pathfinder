package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class res {
    //const vars
    static final int cw = 50; //cell width
    static final int ch = 50; //cell height
    static Texture tex = new Texture(Gdx.files.internal("tile.png"));
    static Texture grid = new Texture(Gdx.files.internal("grid.png"));
}
