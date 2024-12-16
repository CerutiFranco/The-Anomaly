package entidades.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import utiles.AnimacionUtiles;

public class Esqueleto extends Enemigo{
    public Esqueleto(float x, float y,int rangoPatrulla) {
        super("enemigos/esqueleto animacion.png", x, y,17,25,rangoPatrulla,80,5);
        Texture textura = new Texture("enemigos/esqueleto animacion.png");
        caminar = AnimacionUtiles.crearAnimacion(textura, 17, 25, 4, 0, 0.2f);
    }
  }
