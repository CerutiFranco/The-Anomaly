package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import utiles.Render;

public class Imagen {
	private Texture t;
	private Sprite s;
	private int ancho;
	private int alto;

	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
		ancho = t.getWidth();
		alto = t.getHeight();
	}

	public void dibujar() {
		s.setColor(Render.batch.getColor());
		s.draw(Render.batch);
	}

	public void setSize(float ancho, float alto) {
		s.setSize(ancho, alto);
	}

	public void setPosition(float x, float y) {
		s.setPosition(x, y); // Cambia las coordenadas de la imagen
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
