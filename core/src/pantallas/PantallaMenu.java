package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import elementos.Imagen;
import elementos.Texto;
import entradas_salidas.Entradas;
import utiles.Render;

public class PantallaMenu implements Screen {

	Imagen fondo;

	final float anchoPantalla = 1280;
	final float altoPantalla = 720;

	Entradas entradas = new Entradas();
	int opc = 1;
	String opciones[] = {"Comenzar partida", "Opciones", "Salir"};
	private Texto[] textos = new Texto[3];
	public float tiempo = 0;
	private boolean[] mouseSobreOpcion;
	Sound sonido;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		fondo = new Imagen("fondos/selva.jpg");
		fondo.setSize(anchoPantalla, altoPantalla);

		int avance = 40;

		for (int i = 0; i < textos.length; i++) {
			textos[i] = new Texto();
			textos[i].setTexto(opciones[i]);
			textos[i].setPosition((anchoPantalla/2) - (textos[i].getWidth()/2), (altoPantalla/2) + (textos[0].getHeight()/2) - (textos[i].getHeight()*i) + (avance*i));
		}
		mouseSobreOpcion = new boolean[opciones.length];
		for (int i = 0; i < mouseSobreOpcion.length; i++) {
			mouseSobreOpcion[i] = false;
		}

		Gdx.input.setInputProcessor(entradas);
		sonido=Gdx.audio.newSound(Gdx.files.internal("audios/click_sound_1.mp3"));
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();

		fondo.dibujar();

		for (int i = 0; i < textos.length; i++) {
			textos[i].drawCenteredText(opciones[i], 0, (altoPantalla / 2 +100) - (i * 50), anchoPantalla);
		}
		Render.batch.end();

		int mouseX = entradas.getMouseX();
		int mouseY = entradas.getMouseY();
		for (int i = 0; i < textos.length; i++) {
			float textoX = (anchoPantalla / 2) - (textos[i].getWidth() / 2);
			float textoY = (altoPantalla / 2 + 100) - (i * 50);

			// Comprobar si el mouse está sobre el texto
			//if (mouseX >= textoX && mouseX <= textoX + textos[i].getWidth() &&
			//		mouseY >= textoY - textos[i].getHeight() && mouseY <= textoY) {
			//	opc = i + 1; // Ajustar la opción seleccionada
			//	sonido.play();
			//}
			boolean mouseSobreAhora = mouseX >= textoX && mouseX <= textoX + textos[i].getWidth() &&
					mouseY >= textoY - textos[i].getHeight() && mouseY <= textoY;

			if (mouseSobreAhora && !mouseSobreOpcion[i]) { // Si el mouse acaba de entrar
				sonido.play();
				mouseSobreOpcion[i] = true; // Marcar que el mouse ya está sobre esta opción
			} else if (!mouseSobreAhora) { // Si el mouse ya no está sobre esta opción
				mouseSobreOpcion[i] = false; // Restablecer el estado
			}

			if (mouseSobreAhora) {
				opc = i + 1; // Actualizar la opción seleccionada
			}
		}


		for (int i = 0; i < textos.length; i++) {
			if (i == (opc - 1)) {
				textos[i].setColor(Color.SKY);
			} else {
				textos[i].setColor(Color.WHITE);
			}
		}

		tiempo += delta;
		if (entradas.isAbajo() && tiempo > 0.2f) {
			tiempo = 0;
			opc = (opc % textos.length) + 1;
			sonido.play();
		}
		if (entradas.isArriba() && tiempo > 0.2f) {
			tiempo = 0;
			opc = (opc - 2 + textos.length) % textos.length + 1;
			sonido.play();
		}

		switch (opc) {
		case 1:
			if (entradas.isEnter() || entradas.isClick()) {
				Render.app.setScreen(new PantallaJuego());
			}
			break;
			case 2:
				break;
			case 3:
				if (entradas.isEnter() || entradas.isClick()) {
					Gdx.app.exit();
				}
				break;
		}




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
