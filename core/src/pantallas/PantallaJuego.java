package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import entradas_salidas.Entradas;
import utiles.Render;

public class PantallaJuego implements Screen
{
	private Texture aventurero;
	private TextureRegion aventureroRegion;
	private Sprite s;
	private float x,y;
	private Entradas entradas;
	private Animation<TextureRegion> caminarDerecha;
	private float estadoTiempo;
	private TextureRegion[] framesCaminar;
	private Sprite frameInicial;
	private TextureRegion quieto;
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camara;
	private float mapWidth, mapHeight;
	private float escalaMapa = 1.5f;
	
	
	
	
	@Override
	public void show() {
		entradas = new Entradas();
		
		map = new TmxMapLoader().load("mapas/Nivel4.tmx");
		
		render = new OrthogonalTiledMapRenderer(map, escalaMapa);
		
		int mapWidthInTiles = map.getProperties().get("width", Integer.class);
        int mapHeightInTiles = map.getProperties().get("height", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        
        
        mapWidth = mapWidthInTiles * escalaMapa  * tileWidth;
        mapHeight = mapHeightInTiles * escalaMapa * tileHeight;
        
        camara = new OrthographicCamera();
		camara.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        
		aventurero = new Texture("personaje/adventurer-Sheet.png");
		aventureroRegion = new TextureRegion(aventurero, 350, 407);
		TextureRegion[][] temp = aventureroRegion.split(50, 37);
		framesCaminar = new TextureRegion[6];
		int indice = 0;
		for (int i = 1; i < 2; i++) {
			for (int j = 1; j < 7; j++) {
				framesCaminar[indice++] = temp[i][j];
			}
			
		}
		
		s = new Sprite(framesCaminar[0]);
		s.setSize(100, 100);
		s.setPosition(0, 0);
		caminarDerecha = new Animation<TextureRegion>(0.1f, framesCaminar);
		estadoTiempo = 0f;
		quieto = temp[0][0];
		Gdx.input.setInputProcessor(entradas);
			
		camara.position.set(mapWidth / 2, mapHeight / 2, 0);
		
		camara.update();
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		x = s.getX();
		y = s.getY();
		if (entradas.isDerecha()) {
			x += 100 * delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
			s.setRegion(frame);
		} else if (entradas.isIzquierda()) {
			x -= 100 * delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
			s.setRegion(frame);
		} else {
			s.setRegion(quieto);
		}
		s.setPosition(x, y);
		
		
		Render.batch.begin();
		camara.update();
		render.setView(camara);
		render.render();
		s.draw(Render.batch);
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
		map.dispose();
		render.dispose();
	}

}
