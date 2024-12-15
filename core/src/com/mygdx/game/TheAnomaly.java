package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pantallas.PantallaMenu;
import utiles.Render;

public class TheAnomaly extends Game {
	Music musica;
	@Override
	public void create() {
		musica= Gdx.audio.newMusic(Gdx.files.internal("audios/TremLoadingloopl.wav"));
		musica.setLooping(true);
		Render.app = this;
		Render.batch = new SpriteBatch();
		this.setScreen(new PantallaMenu());
		musica.play();
		musica.setVolume(0.3f);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {

		Render.batch.dispose();
		musica.dispose();
	}
}
