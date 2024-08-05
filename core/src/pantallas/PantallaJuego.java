package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import elementos.Imagen;
import utiles.Render;

public class PantallaJuego implements Screen
{
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer otmr;
	
	@Override
	public void show() {
	    tiledMap = new TmxMapLoader().load("Nivel3.tmx");
	    otmr = new OrthogonalTiledMapRenderer(tiledMap);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		otmr.render();
		
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
