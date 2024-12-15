package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Slime;
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
	private Array<Enemigo> enemigos;

	public PantallaJuego() {
		this.jugador = new Jugador();
		this.entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
		this.camara = new Camara();
		rectangulosColision = new Array<>();
		this.enemigos = new Array<>();

	}
	@Override
	public void show() {
		map = new Mapa("mapas/mapa total.tmx");
		render = new OrthogonalTiledMapRenderer(map.getTiled(), map.getEscalaMapa());
		Gdx.input.setInputProcessor(entradas);
		rectangulosColision = new Array<>();
		for (MapObject object : map.getTiled().getLayers().get("Colisiones").getObjects()) {
			if (object instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				rectangulosColision.add(rect);
			}
		}
		inicializarEnemigos();
		for (Enemigo enemigo : enemigos) {
			enemigo.ajustarPosicion(rectangulosColision);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
			Render.cambiarPantalla(new PantallaPausa(this,camara));
		}
		Direcciones direccion = entradas.getDireccion();

		jugador.mover(direccion, delta,rectangulosColision);

		for (Enemigo enemigo : enemigos) {
			enemigo.actualizar(delta, jugador.getSprite().getX(), jugador.getSprite().getY(), rectangulosColision);
		}

		jugador.actualizar(delta);
		verificarColisionEnemigos();

		float limiteDerechoViewport = camara.getCamara().position.x + camara.getViewportWidth() / 2;
		float jugadorDerecha = jugador.getSprite().getX() + jugador.getSprite().getWidth();

		if (jugadorDerecha >= limiteDerechoViewport) {
			camara.moverACamaraSiguiente(); // Cambiar la posición de la cámara según tu lógica
		}

		Render.batch.begin();

		camara.actualizar();
		render.setView(camara.getCamara());
		render.render();

		jugador.dibujar(Render.batch);

		for (Enemigo enemigo : enemigos) {
			enemigo.draw(Render.batch, 1.0f);
		}
		Render.batch.end();


	}

	@Override
	public void resize(int width, int height) {
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
	private void inicializarEnemigos() {
		// Ejemplo: Crear un slime en posición (200, 100)
		Slime slime = new Slime(500, 500, 100);
		boolean enColision = false;
		for (Rectangle rect : rectangulosColision) {
			if (slime.getHitbox().overlaps(rect)) {
				slime.setPosition(slime.getX(), rect.getY() + rect.getHeight());
				break;
			}
		}
		if (enColision) {
			System.out.println("El slime está en colisión. Ajustando posición inicial...");
			slime.ajustarPosicion(rectangulosColision);
		}
		enemigos.add(slime);
	}

	private void verificarColisionEnemigos() {
		Rectangle hitboxJugador = jugador.getHitbox();

		for (Enemigo enemigo: enemigos) {
			Rectangle hitboxEnemigo = enemigo.getHitbox();
			if (hitboxJugador.overlaps(hitboxEnemigo)) {
				jugador.recibirDanio(1); // Reducir la vida en 1
			}
			if(jugador.getVida()==0){
				Render.cambiarPantalla(new PantallaPerder(camara));
			}
		}
	}

	private void dibujarBarraVida() {

	}
}
