package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import pantallas.PantallaMenu;
import utiles.Render;

public class Texto {
	BitmapFont fuente;
	int anchoPantalla =1280;
	int altoPantalla=720;
	
	public Texto() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/PressStart2P.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
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

	public void drawCenteredText(String text, float y) {
        float textWidth = fuente.getRegion().getRegionWidth();
        float textHeight = fuente.getCapHeight();
        fuente.draw(Render.batch, text, (anchoPantalla - textWidth) / 2, y);
    }
	public void setColor(Color color) {
		fuente.setColor(color);
	}
}
