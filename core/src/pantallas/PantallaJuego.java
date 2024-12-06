package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entidades.Jugador;
import entradas_salidas.Direcciones;
import entradas_salidas.Entradas;
import utiles.Render;
import elementos.Mapa;

public class PantallaJuego implements Screen {
	private Entradas entradas;
	private Mapa map;
	private OrthogonalTiledMapRenderer render;
	private Jugador jugador;
	private Array<Rectangle> rectangulosColision;
	private Camara camara;


	public PantallaJuego() {
		this.jugador = new Jugador();
		this.entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
		camara=new Camara();
		rectangulosColision = new Array<>();
	}
	@Override
	public void show() {
		map = new Mapa("mapas/Mapa prueba.tmx");
		render = new OrthogonalTiledMapRenderer(map.getTiled(), map.getEscalaMapa());
		Gdx.input.setInputProcessor(entradas);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Direcciones direccion = entradas.getDireccion();
		jugador.mover(direccion, delta,rectangulosColision);

		Render.batch.begin();
		camara.actualizar();
		render.setView(camara.getCamara());
		render.render();

		jugador.dibujar(Render.batch);
		Render.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		camara.resize(width, height);
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
