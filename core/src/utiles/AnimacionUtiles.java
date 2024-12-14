package utiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimacionUtiles {
    public static Animation<TextureRegion> crearAnimacion(Texture textura, int anchoFrame, int altoFrame, int columnas, int fila, float duracion) {
        TextureRegion[][] temp = TextureRegion.split(textura, anchoFrame, altoFrame);

        TextureRegion[] frames = new TextureRegion[columnas];
        for (int i = 0; i < columnas; i++) {
            frames[i] = temp[fila][i];
        }
        return new Animation<>(duracion, frames);
    }
}
