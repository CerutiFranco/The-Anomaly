package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pantallas.PantallaMenu;
import utiles.Render;

public class TheAnomaly extends Game {
	Texture img;
	Sprite sprite;
	
	@Override
	public void create () {
		Render.app=this;
		Render.batch= new SpriteBatch();
		this.setScreen(new PantallaMenu());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();
	}
}
