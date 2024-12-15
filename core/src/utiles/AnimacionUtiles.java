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
    public static TextureRegion[] cargarTextureRegion(TextureRegion[] textureRegion,Texture textura,int anchoSprite,int altoSprite,int cantidadFilas){
        TextureRegion[][] temp = new TextureRegion(textura).split(anchoSprite, altoSprite);
        int indice = 0;
        for (int i = 0; i <cantidadFilas; i++) {
            textureRegion[indice++] = temp[i][0];
        }
        return textureRegion;
    }
}
