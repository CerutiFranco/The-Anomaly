package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import elementos.Imagen;
import utiles.Render;

public class PantallaJuego implements Screen
{
	Texture aventurero;
	TextureRegion aventureroRegion;
//	Sprite s = new Sprite(aventureroRegion);
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	
	
	
	@Override
	public void show() {
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("mapas/Nivel3.tmx");
		render = new OrthogonalTiledMapRenderer(map);
		
//		aventurero = new Texture("personaje/adventurer-Sheet.png");
//		aventureroRegion = new TextureRegion(aventurero, 350, 407);
//		TextureRegion[][] temp = aventureroRegion.split(50, 37);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
			render.render();
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
