package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camara;
	
	
	
	@Override
	public void show() {
		
		map = new TmxMapLoader().load("mapas/Nivel4.tmx");
		render = new OrthogonalTiledMapRenderer(map);
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		aventurero = new Texture("personaje/adventurer-Sheet.png");
//		aventureroRegion = new TextureRegion(aventurero, 350, 407);
//		TextureRegion[][] temp = aventureroRegion.split(50, 37);
		
		//camara.position.set(mapWidth / 2, mapHeight / 2, 0);
		camara.update();
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camara.update();
		
		render.setView(camara);
		
		render.render();
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
