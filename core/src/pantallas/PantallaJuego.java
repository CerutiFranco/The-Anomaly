package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Array;
import entidades.Jugador;
import entidades.Personaje;
import entradas_salidas.Direcciones;
import entradas_salidas.Entradas;
import utiles.Render;
import elementos.Mapa;

public class PantallaJuego implements Screen {
	private Entradas entradas;
	private Mapa map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camara;
	private Jugador jugador;
	private Array<Rectangle> rectangulosColision;

	public PantallaJuego() {
		this.jugador = new Jugador();
		this.entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
	}
	@Override
	public void show() {
		Personaje jugador= new Jugador();
		map = new Mapa("mapas/Nivel4.tmx", 1.4f);
		map.ObtenerDimensiones();

		render = new OrthogonalTiledMapRenderer(map.getTiled(), map.getEscalaMapa());

		camara = new OrthographicCamera();
		camara.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		Gdx.input.setInputProcessor(entradas);

		camara.position.set(map.getWidth() / 2, map.getHeight() / 2, 0);

		camara.update();

		rectangulosColision = new Array<>();

		// Carga los objetos de la capa de colisión
		if (map.getTiled().getLayers().get("Collisions") != null) {
			for (MapObject object : map.getTiled().getLayers().get("Collisions").getObjects()) {
				if (object instanceof RectangleMapObject) {
					Rectangle rect = ((RectangleMapObject) object).getRectangle();
					rectangulosColision.add(rect);
				}
			}
			System.out.println("Rectángulos de colisión cargados: " + rectangulosColision.size);
		} else {
			System.out.println("Error: La capa 'Collisions' no existe en el mapa.");
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Direcciones direccion = entradas.getDireccion();
		jugador.mover(direccion, delta,rectangulosColision);



		camara.position.set(jugador.getSprite().getX() + jugador.getSprite().getWidth() / 2, jugador.getSprite().getY() + jugador.getSprite().getHeight() / 2, 0);
		camara.update();

		Render.batch.begin();
		camara.update();
		render.setView(camara);
		render.render();

		jugador.dibujar(Render.batch);
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
