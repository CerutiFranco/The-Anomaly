package entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Personaje extends Actor {

	Texture aventurero = new Texture("personaje/adventurer-Sheet.png");
	TextureRegion aventureroRegion = new TextureRegion(aventurero, 350, 407);

	public Personaje(TextureRegion personaje) {

	}

	public void dispose() {

	}
}
