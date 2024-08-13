package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
<<<<<<< HEAD
=======
import com.badlogic.gdx.graphics.OrthographicCamera;
>>>>>>> 86d8190d2e9207ea3e510d78d58097c8d4e338c7
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

<<<<<<< HEAD
import elementos.Imagen;
=======
>>>>>>> 86d8190d2e9207ea3e510d78d58097c8d4e338c7
import entradas_salidas.Entradas;
import utiles.Render;
import elementos.Mapa;

<<<<<<< HEAD
public class PantallaJuego implements Screen
{
	Texture aventurero= new Texture("personaje/adventurer-Sheet.png");
	TextureRegion aventureroRegion;
	Sprite s;
	 float y,x;
	 Entradas entradas= new Entradas();
	 Animation<TextureRegion> caminarDerecha;
	 float estadoTiempo;
	 TextureRegion[] framesCaminar;
	 Sprite frameInicial;
	 TextureRegion quieto;
	@Override
	public void show() {
		aventurero= new Texture("personaje/adventurer-Sheet.png");
		aventureroRegion = new TextureRegion(aventurero,350,407);
		TextureRegion[][] temp=aventureroRegion.split(50, 37);
		framesCaminar = new  TextureRegion[6];
		int indice=0;
		for(int i=1;i<2;i++) {
			for(int j=1;j<7;j++) {
				framesCaminar[indice++]=temp[i][j];
			}
		}
		s = new Sprite(framesCaminar[0]);
        s.setSize(100, 100);
        s.setPosition(0, 0); 
		caminarDerecha = new Animation<TextureRegion>(0.1f, framesCaminar);
		estadoTiempo = 0f; 		
		quieto = temp[0][0]; 
		Gdx.input.setInputProcessor(entradas);
=======
public class PantallaJuego implements Screen {
	private Texture aventurero;
	private TextureRegion aventureroRegion;
	private Sprite s;
	private float x, y;
	private Entradas entradas = new Entradas();
	private Animation<TextureRegion> caminarDerecha;
	private float estadoTiempo;
	private TextureRegion[] framesCaminar;
	private TextureRegion quieto;
	private Mapa map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camara;
	@Override
	public void show() {
		
		map = new Mapa("mapas/Nivel4.tmx", 1.4f);
		map.ObtenerDimensiones();
		
		render = new OrthogonalTiledMapRenderer(map.getTiled(), map.getEscalaMapa());

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
		s.setPosition(0, 120);
		caminarDerecha = new Animation<TextureRegion>(0.1f, framesCaminar);
		estadoTiempo = 0f;
		quieto = temp[0][0];
		Gdx.input.setInputProcessor(entradas);

		camara.position.set(map.getWidth() / 2, map.getHeight() / 2, 0);

		camara.update();
>>>>>>> 86d8190d2e9207ea3e510d78d58097c8d4e338c7
	}
	
	@Override
	public void render(float delta) {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		x=s.getX();
		y=s.getY();
		if(entradas.isDerecha()) {
			x+=100*delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
            s.setRegion(frame);
		}
		else if (entradas.isIzquierda()) {
			x -= 100*delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
            s.setRegion(frame);
		}
		else {
			s.setRegion(quieto);
		}
		s.setPosition(x, y);
	    
		Render.batch.begin();
=======

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
		} else if (entradas.isAbajo()) {
			y -= 100 * delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
			s.setRegion(frame);
		} else if (entradas.isArriba()) {
			y += 100 * delta;
			estadoTiempo += delta;
			TextureRegion frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
			s.setRegion(frame);
		} else {
			s.setRegion(quieto);
		}
		s.setPosition(x, y);

		camara.position.set(s.getX() + s.getWidth() / 2, s.getY() + s.getHeight() / 2, 0);
		camara.update();

		Render.batch.begin();
		camara.update();
		render.setView(camara);
		render.render();
>>>>>>> 86d8190d2e9207ea3e510d78d58097c8d4e338c7
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
		map.getTiled().dispose();
		render.dispose();
	}

}
