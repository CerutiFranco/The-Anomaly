package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import elementos.Imagen;
import utiles.Render;

public class PantallaJuego implements Screen
{
	Texture aventurero= new Texture("personaje/adventurer-Sheet.png");
	TextureRegion aventureroRegion;
	Sprite s= new Sprite(aventureroRegion);
	Sprite miamor;
	@Override
	public void show() {
		aventurero= new Texture("personaje/adventurer-Sheet.png");
		aventureroRegion = new TextureRegion(aventurero,350,407);
		TextureRegion[][] temp=aventureroRegion.split(50, 37);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
		
		Render.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
