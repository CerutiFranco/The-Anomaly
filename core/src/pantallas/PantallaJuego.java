package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Esqueleto;
import entidades.enemigos.Slime;
import entidades.Jugador;
import entradas_salidas.Direcciones;
import entradas_salidas.Entradas;
import utiles.Render;
import elementos.Mapa;

import java.util.Iterator;


public class PantallaJuego implements Screen {
	private Entradas entradas;
	private Mapa map;
	private OrthogonalTiledMapRenderer render;
	private Jugador jugador;
	private Array<Rectangle> rectangulosColision;
	private Camara camara;
	private Array<Enemigo> enemigos;
	private TextureRegion[] barraVida;

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
		Texture texturaBarraVida = new Texture("barras/Barras de vida.png");

		// Dividir el spritesheet en regiones de 61x15 (5 filas, 1 columna)
		TextureRegion[][] temp = TextureRegion.split(texturaBarraVida, 61, 15);

		// Guardar las regiones en un arreglo lineal
		barraVida = new TextureRegion[5]; // 5 estados de la barra
		for (int i = 0; i < 5; i++) {
			barraVida[i] = temp[i][0]; // Cada fila representa un estado de la barra
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

		Direcciones direccion = entradas.getDireccion();

		jugador.mover(direccion, delta,rectangulosColision);

		for (Enemigo enemigo : enemigos) {
			enemigo.actualizar(delta, jugador.getSprite().getX(), jugador.getSprite().getY(), rectangulosColision);
		}
		jugador.actualizar(delta);
		verificarColisionConEnemigos();
		if (jugador.getEspada().isAtacando()) {
			for (Enemigo enemigo : enemigos) {
				if (jugador.getEspada().getHitbox().overlaps(enemigo.getHitbox())) {
					boolean ataqueDesdeDerecha = jugador.getSprite().getX() < enemigo.getX();
					enemigo.recibirDanio(ataqueDesdeDerecha);
					System.out.println("¡Golpeaste al enemigo!");
				}
			}
		}
		Iterator<Enemigo> iterador = enemigos.iterator();
		while (iterador.hasNext()) {
			Enemigo enemigo = iterador.next();
			if (enemigo.getVida() <= 0) {
				iterador.remove();
				System.out.println("Enemigo eliminado");
			}
		}

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
		dibujarBarraVida();

		Render.batch.end();

		if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
			Render.cambiarPantalla(new PantallaPausa(this,camara));
		}
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

		enemigos.add(new Slime(500, 500,100));
		enemigos.add(new Esqueleto(300, 300,100));
		for(int i=0;i<enemigos.size;i++){
			boolean enColision = false;
			Enemigo enemigo=enemigos.get(i);
			for (Rectangle rect : rectangulosColision) {
				if (enemigo.getHitbox().overlaps(rect)) {
					enemigo.setPosition(enemigo.getX(), rect.getY() + rect.getHeight());
					enColision = true;
					break;
				}
			}
			if (enColision) {
				enemigo.ajustarPosicion(rectangulosColision);
			}
		}


	}
	private void verificarColisionConEnemigos() {
		// Obtener la hitbox del jugador
		Rectangle hitboxJugador = jugador.getHitbox();

		for (Enemigo enemigo : enemigos) {
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

		// Proyección de UI (pantalla completa)
		Matrix4 uiMatrix = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Render.batch.setProjectionMatrix(uiMatrix);

		float escala = 2.0f;
		float ancho = barraVida[0].getRegionWidth() * escala;
		float alto = barraVida[0].getRegionHeight() * escala;

		float x = Gdx.graphics.getWidth() - ancho - 50; // Derecha
		float y = Gdx.graphics.getHeight() - alto - 50; // Superior

		int indiceBarra = Math.max(0, Math.min(5 - jugador.getVida(), 4));
		Render.batch.draw(barraVida[indiceBarra], x, y, ancho, alto);

		// Restaurar matriz original
		Render.batch.setProjectionMatrix(camara.getCamara().combined);
	}
}