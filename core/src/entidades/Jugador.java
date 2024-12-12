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
        private float posicionYInicial;
        private boolean enElAire = false;

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
            sprite.setSize(80, 80);
            sprite.setPosition(0, 180);
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
                    if (!saltando && !cayendo) { // Solo usa la animación de caminar si está en el suelo
                        frame = caminarIzquierda.getKeyFrame(estadoTiempo, true);
                        estadoTiempo += delta;
                    }

                    break;
                case ARRIBA:
                    if (!enElAire) { // Solo salta si no está en el aire
                        enElAire= true;
                        velocidadY = velocidadSalto;
                    }
                    frame = saltar.getKeyFrame(estadoTiempo,false); // Obtén la animación del salto
                    estadoTiempo += delta;
                    break;
                case QUIETO:
                default:
                    if (!enElAire) { // Quieto solo aplica si no está en el aire
                        frame = quieto;
                    }
                    break;
            }

            if (enElAire) {
                velocidadY += gravedad * delta;
                nuevoY += velocidadY * delta;
            }

            // Verificar si está sobre una plataforma
            // Colisiones verticales
            if (verificarColision(nuevoX, nuevoY, colisionables)) {
                if (velocidadY > 0) { // Golpeando la parte inferior de una plataforma
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

            // Actualizar posición solo si no hay colisión
            //colisiones horizontales
            if (verificarColision(nuevoX, y, colisionables)) {
                if(nuevoX > x) {
                    nuevoX = ajustarALadoIzquierdo(x, colisionables);
                } else if (nuevoX < x) {
                    nuevoX = ajustarALadoDerecho(x, colisionables);
                }
                //nuevoX = x; // Si hay colisión horizontal, no mover en X
            }
            
            sprite.setPosition(nuevoX, nuevoY);
            sprite.setRegion(frame);
    }
    private float ajustarAPlataforma(float x, float y, Array<Rectangle> colisionables) {
        float hitboxWidth = 17f;
        float hitboxHeight = 27f;
        float offsetX = (sprite.getWidth() - hitboxWidth) / 2;
        float offsetY = (sprite.getHeight() - hitboxHeight) / 2;

        Rectangle rectJugador = new Rectangle(
                x + offsetX,
                y + offsetY,
                hitboxWidth,
                hitboxHeight
        );

        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                if(y + hitboxHeight <= rect.getY()){
                    return y;
                }
                // Ajustar la posición Y considerando el tamaño del sprite y el offset
                return rect.getY() + rect.getHeight() - offsetY;
            }
        }
        return y;
    }

    private float ajustarALadoIzquierdo(float x, Array<Rectangle> colisionables) {
        float hitboxWidth = 17f;
        float hitboxHeight = 27f;
        float offsetX = (sprite.getWidth() - hitboxWidth) / 2;
        float offsetY = (sprite.getHeight() - hitboxHeight) / 2;

        Rectangle rectJugador = new Rectangle(x + offsetX, sprite.getY() + offsetY, hitboxWidth, hitboxHeight);
        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() - hitboxWidth - offsetX;
            }
        }
        return x;
    }

    private float ajustarALadoDerecho(float x, Array<Rectangle> colisionables) {
        float hitboxWidth = 17f;
        float hitboxHeight = 27f;
        float offsetX = (sprite.getWidth() - hitboxWidth) / 2;
        float offsetY = (sprite.getHeight() - hitboxHeight) / 2;

        Rectangle rectJugador = new Rectangle(x + offsetX, sprite.getY() + offsetY, hitboxWidth, hitboxHeight);
        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() + rect.getWidth() - offsetX;
            }
        }
        return x;
    }


    public boolean verificarColision(float x, float y, Array<Rectangle> colisionables) {
        float hitboxWidth = 17f; // Ancho real del personaje
        float hitboxHeight = 27f; // Alto real del personaje
        float offsetX = (sprite.getWidth() - hitboxWidth) / 2; // Centro horizontal del sprite
        float offsetY = (sprite.getHeight() - hitboxHeight) / 2; // Centro vertical del sprite

        // Crear el rectángulo de colisión con las dimensiones ajustadas
        Rectangle rectJugador = new Rectangle(x + offsetX, y + offsetY, hitboxWidth, hitboxHeight);
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



	
}
  

