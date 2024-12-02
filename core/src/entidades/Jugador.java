package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entradas_salidas.Direcciones;


public class Jugador extends Personaje{
        private Animation<TextureRegion> caminarDerecha;
        private float estadoTiempo;
        private TextureRegion quieto;
        private Sprite sprite;

        public Jugador() {
            Texture textura = new Texture("personaje/Spritesheet principal-0001 - copia.png");
            TextureRegion[][] temp = new TextureRegion(textura).split(32, 32);

            // Configurar animaciones
            TextureRegion[] framesCaminar = new TextureRegion[4];
            int indice = 0;
            for (int i = 2; i <3; i++) {
                for (int j = 0; j < 4; j++) {
                    framesCaminar[indice++] = temp[i][j];
                }
            }
            caminarDerecha = new Animation<>(0.1f, framesCaminar);
            quieto = temp[0][0];

            // Configurar sprite
            sprite = new Sprite(quieto);
            sprite.setSize(100, 100);
            sprite.setPosition(0, 120);
        }

        public void mover(Direcciones direccion, float delta) {
            float x = sprite.getX();
            float y = sprite.getY();
            TextureRegion frame;

            switch (direccion) {
                case DERECHA:
                    x += 100 * delta;
                    frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                    estadoTiempo += delta;
                    break;
                case IZQUIERDA:
                    x -= 100 * delta;
                    frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                    estadoTiempo += delta;
                    break;
                case ABAJO:
                    y -= 100 * delta;
                    frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                    estadoTiempo += delta;
                    break;
                case ARRIBA:
                    y += 100 * delta;
                    frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                    estadoTiempo += delta;
                    break;
                case QUIETO:
                default:
                    frame = quieto;
                    break;
            }

            sprite.setPosition(x, y);
            sprite.setRegion(frame);
        }

        public void dibujar(SpriteBatch batch) {
            sprite.draw(batch);
        }

        public Sprite getSprite() {
            return sprite;
        }


	
}
  

