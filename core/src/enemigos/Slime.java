package enemigos;

import com.badlogic.gdx.graphics.Texture;
import utiles.AnimacionUtiles;

public class Slime extends Enemigo{


    public Slime(float x,float y) {
        super(x, y,16,19,100);
        Texture textura = new Texture("enemigos/animacion slime.png");
        caminar = AnimacionUtiles.crearAnimacion(textura, 16, 19, 4, 0, 0.1f);
    }

}
