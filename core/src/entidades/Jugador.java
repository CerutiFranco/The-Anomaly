package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entradas_salidas.Direcciones;


public class Jugador extends Personaje{
        private Animation<TextureRegion> caminarDerecha;
        private Animation<TextureRegion> caminarIzquierda;
        private Animation<TextureRegion> saltar;
        private float estadoTiempo;
        private TextureRegion quieto;
        private Sprite sprite;
        private boolean saltando = false;
        private boolean cayendo = false;
        private float velocidadY = 0;
        private final float gravedad = -500f; 
        private final float velocidadSalto = 300f;
        private boolean enElAire = false;

        public Jugador() {
            Texture textura = new Texture("personaje/Spritesheet jugador.png");
            TextureRegion[][] temp = new TextureRegion(textura).split(32, 32);

            // Configurar animaciones
            TextureRegion[] framesCaminarDerecha = new TextureRegion[4];
            TextureRegion[] framesCaminarIzquierda= new TextureRegion[4];
            TextureRegion[] framesSaltar= new TextureRegion[4];
            caminarDerecha = new Animation<>(0.1f, configuraranimacion(framesCaminarDerecha,2,temp));
            caminarIzquierda= new Animation<>(0.1f, configuraranimacion(framesCaminarIzquierda,3,temp));
            saltar= new Animation<>(0.3f, configuraranimacion(framesSaltar,4,temp));
            quieto = temp[0][0];

            // Configurar sprite
            sprite = new Sprite(quieto);
            sprite.setSize(80, 80);
            sprite.setPosition(0, 150);

        }

        public TextureRegion[] configuraranimacion(TextureRegion[] framesAnimacion,int filaAnimacion,TextureRegion[][] temp){
            int indice = 0;
            for (int i = filaAnimacion-1; i <filaAnimacion; i++) {
                for (int j = 0; j < 4; j++) {
                    framesAnimacion[indice++] = temp[i][j];
                }
            }
            return framesAnimacion;
        }

        public void mover(Direcciones direccion, float delta,Array<Rectangle> colisionables) {
            float x = sprite.getX();
            float y = sprite.getY();
            float nuevoX = x, nuevoY = y;
            TextureRegion frame = quieto;

            switch (direccion) {
                case DERECHA:
                    nuevoX += 100 * delta;
                    if (!saltando && !cayendo) { // Solo usa la animación de caminar si está en el suelo
                        frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                        estadoTiempo += delta;
                    }

                    break;
                case IZQUIERDA:
                    nuevoX -= 100 * delta;
                    if (!enElAire) {
                        frame = caminarIzquierda.getKeyFrame(estadoTiempo, true);
                        estadoTiempo += delta;
                    }

                    break;
                case ARRIBA:
                    if (!enElAire) {
                        enElAire= true;
                        velocidadY = velocidadSalto;
                    }
                    frame = saltar.getKeyFrame(estadoTiempo,false);
                    estadoTiempo += delta;
                    break;
                case QUIETO:
                default:
                    if (!enElAire) {
                        frame = quieto;
                    }
                    break;
            }

            if (enElAire) {
                velocidadY += gravedad * delta;
                nuevoY += velocidadY * delta;
            }

            if (verificarColision(nuevoX, nuevoY, colisionables)) {
                if (velocidadY > 0) {
                    velocidadY = 0;
                    nuevoY = y;
                } else if (velocidadY < 0) { // Aterrizando en una plataforma
                    enElAire = false;
                    velocidadY = 0;
                    nuevoY = ajustarAPlataforma(nuevoX, nuevoY, colisionables);
                }
            } else if (!verificarColision(nuevoX, nuevoY - 1, colisionables)) {
                enElAire = true; // Empezar a caer si no hay soporte debajo
            }

            if (verificarColision(nuevoX, y, colisionables)) {
                if(nuevoX > x) {
                    nuevoX = ajustarALadoIzquierdo(x, colisionables);
                } else if (nuevoX < x) {
                    nuevoX = ajustarALadoDerecho(x, colisionables);
                }
            }
            
            sprite.setPosition(nuevoX, nuevoY);
            this.setPosition(nuevoX, nuevoY);
            sprite.setRegion(frame);
    }
    private float ajustarAPlataforma(float x, float y, Array<Rectangle> colisionables) {
        float hitboxWidth = 42.5f;
        float hitboxHeight = 67.5f;

        Rectangle rectJugador = new Rectangle(x, y, hitboxWidth, hitboxHeight);

        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                // Ajustar al borde superior de la plataforma
                return rect.getY() + rect.getHeight();
            }
        }
        return y;
    }

    private float ajustarALadoIzquierdo(float x, Array<Rectangle> colisionables) {
        float hitboxWidth = 42.5f;
        float hitboxHeight = 67.5f;

        Rectangle rectJugador = new Rectangle(x, sprite.getY(), hitboxWidth, hitboxHeight);
        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() - hitboxWidth;
            }
        }
        return x;
    }

    private float ajustarALadoDerecho(float x, Array<Rectangle> colisionables) {
        float hitboxWidth = 42.5f;
        float hitboxHeight = 67.5f;

        Rectangle rectJugador = new Rectangle(x, sprite.getY(), hitboxWidth, hitboxHeight);
        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() + rect.getWidth();
            }
        }
        return x;
    }


    public boolean verificarColision(float x, float y, Array<Rectangle> colisionables) {
        float hitboxWidth = 42.5f; // Ancho escalado del hitbox
        float hitboxHeight = 67.5f; // Alto escalado del hitbox

        // Crear el rectángulo de colisión con las dimensiones del hitbox
        Rectangle rectJugador = new Rectangle(x, y, hitboxWidth, hitboxHeight);
        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return true;
            }
        }
        return false;
    }




        public void dibujar(SpriteBatch batch) {
            sprite.draw(batch);
        }

        public Sprite getSprite() {
            return sprite;
        }


    public float getPosicionX() {
        return getX();
    }


	
}
  

