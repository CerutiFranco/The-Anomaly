package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import utiles.Render;

import java.awt.*;

public class Texto {
	BitmapFont fuente;
	private float x = 0;
	private float y = 0;
	GlyphLayout layout;
	private String texto = "";

	public Texto() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/PressStart2P.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32; // Tamaño de la fuente
		parameter.color = Color.WHITE; // Color del texto
		parameter.borderWidth = 2; // Ancho del borde
		parameter.borderColor = Color.BLACK; // Color del borde
//	parameter.shadowOffsetX = 3; // Desplazamiento de la sombra en X
//	parameter.shadowOffsetY = 3; // Desplazamiento de la sombra en Y
//	parameter.shadowColor = Color.GRAY; // Color de la sombra
		fuente = generator.generateFont(parameter);
		layout = new GlyphLayout();
		generator.dispose();
	}

	public void drawCenteredText(String text, float x, float y, float anchoDisponible) {
		GlyphLayout layout = new GlyphLayout(fuente, text); // Medir el texto
		float textWidth = layout.width; // Obtener el ancho real del texto
		float posX = x + (anchoDisponible - textWidth) / 2; // Calcular posición centrada
		fuente.draw(Render.batch, text, posX, y); // Dibujar texto
	}

	public void setColor(Color color) {
		fuente.setColor(color);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getWidth() {
		layout.setText(fuente, texto);
		return layout.width;
	}

	public float getHeight() {
		layout.setText(fuente, texto);
		return layout.height;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void dibujar(){
		fuente.draw(Render.batch, texto, x - 150, y - 25);
	}
}
