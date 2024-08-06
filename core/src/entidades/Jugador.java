package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import elementos.Imagen;

public class Jugador extends Actor{
	
	Texture aventurero = new Texture("personaje/adventurer-Sheet.png");
	TextureRegion aventureroRegion = new TextureRegion(aventurero,350,407);
    public Jugador(TextureRegion personaje){
    	
        
    }
    public void dispose() {
    	
    }
}
