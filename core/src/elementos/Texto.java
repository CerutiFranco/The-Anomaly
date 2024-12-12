package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import utiles.Render;

public class Texto {
	BitmapFont fuente;
	int anchoPantalla = 1280;
	int altoPantalla = 720;

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
}
