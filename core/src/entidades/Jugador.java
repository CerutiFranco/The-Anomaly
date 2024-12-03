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
        private final float gravedad = -500f; // Velocidad de caída
        private final float velocidadSalto = 300f; // Velocidad inicial de salto
        private float posicionYInicial;

        public Jugador() {
            Texture textura = new Texture("personaje/Spritesheet principal-0001 - copia.png");
            TextureRegion[][] temp = new TextureRegion(textura).split(32, 32);

            // Configurar animaciones
            TextureRegion[] framesCaminarDerecha = new TextureRegion[4];
            TextureRegion[] framesCaminarIzquierda= new TextureRegion[4];
            TextureRegion[] framesSaltar= new TextureRegion[4];
            caminarDerecha = new Animation<>(0.1f, configuraranimacion(framesCaminarDerecha,3,temp));
            caminarIzquierda= new Animation<>(0.1f, configuraranimacion(framesCaminarIzquierda,4,temp));
            saltar= new Animation<>(0.3f, configuraranimacion(framesSaltar,5,temp));
            quieto = temp[0][0];

            // Configurar sprite
            sprite = new Sprite(quieto);
            sprite.setSize(100, 100);
            sprite.setPosition(0, 120);
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

        public void mover(Direcciones direccion, float delta,Array<Rectangle> rectangulos) {
            float x = sprite.getX();
            float y = sprite.getY();
            float nuevoX = x, nuevoY = y;
            TextureRegion frame=quieto;

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
                    if (!saltando && !cayendo) { // Solo usa la animación de caminar si está en el suelo
                        frame = caminarIzquierda.getKeyFrame(estadoTiempo, true);
                        estadoTiempo += delta;
                    }
                    break;
                case ABAJO:
                    y -= 100 * delta;
                    frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                    estadoTiempo += delta;
                    break;
                case ARRIBA:
                    if (!saltando && !cayendo) { // Solo salta si no está en el aire
                        saltando = true;
                        velocidadY = velocidadSalto;
                        posicionYInicial = y; // Guarda la posición inicial
                        estadoTiempo = 0; // Resetea el tiempo de animación
                    }
                    frame = saltar.getKeyFrame(estadoTiempo,false); // Obtén la animación del salto
                    estadoTiempo += delta;
                    break;
                case QUIETO:
                default:
                    if (!saltando && !cayendo) { // Quieto solo aplica si no está en el aire
                        frame = quieto;
                    }
                    break;
            }
            if (saltando || cayendo) {
                velocidadY += gravedad * delta; // Aplica gravedad
                nuevoY += velocidadY * delta; // Actualiza la posición vertical

                // Si llega a la posición inicial, detén el salto
                if (nuevoY <= posicionYInicial) {
                    nuevoY = posicionYInicial;
                    velocidadY = 0;
                    saltando = false;
                    cayendo = false; // Vuelve a estar en reposo
                } else if (velocidadY < 0) {
                    cayendo = true; // Cambia a estado de caída si la velocidad es negativa
                }
                frame = saltar.getKeyFrame(estadoTiempo, false);
                estadoTiempo += delta; // Incrementa el tiempo de animación
            }

            if (!verificarColision(nuevoX, nuevoY, rectangulos)) {
                sprite.setPosition(nuevoX, nuevoY);
            }else {
                sprite.setPosition(x, y); // Si hay colisión, mantén la posición anterior
            }
            sprite.setRegion(frame);
        }

        public void dibujar(SpriteBatch batch) {
            sprite.draw(batch);
        }

        public Sprite getSprite() {
            return sprite;
        }
    public boolean verificarColision(float x, float y, Array<Rectangle> rectangulos) {
        Rectangle rectJugador = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

        for (Rectangle rect : rectangulos) {
            if (rectJugador.overlaps(rect)) {
                return true; // Hay colisión
            }
        }
        return false; // No hay colisión
    }


	
}
  

