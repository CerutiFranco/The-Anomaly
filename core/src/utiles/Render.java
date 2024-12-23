package utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TheAnomaly;

public class Render {

	public static SpriteBatch batch;

	public static TheAnomaly app;

	public static void LimpiarPantalla() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	public static void cambiarPantalla(Screen screen) {
		if (app != null) {
			app.setScreen(screen);
		} else {
			throw new IllegalStateException("La referencia a 'app' no ha sido inicializada.");
		}
	}

}
