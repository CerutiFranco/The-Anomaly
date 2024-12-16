package entidades;

import armas.Espada;
import com.badlogic.gdx.Gdx;

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
        private Animation<TextureRegion> atacarDerecha;
        private Animation<TextureRegion> atacarIzquierda;
        private float estadoTiempo;
        private TextureRegion quieto;
        private Sprite sprite;
        private boolean saltando = false;
        private boolean cayendo = false;
        private float velocidadY = 0;
        private final float gravedad = -500f; 
        private final float velocidadSalto = 300f;
        private boolean enElAire = false;
        private float hitboxWidth = 42.5f;
        private float hitboxHeight = 67.5f;
        private int vida;
        private boolean invulnerable = false;
        private float tiempoInvulnerabilidad = 0f;
        private final float DURACION_INVULNERABILIDAD = 1f;
        private boolean mirandoDerecha = true;
        private float tiempoAtaque = 0f; // Control del tiempo para la animación de ataque
        private boolean atacando = false;
        private Espada espada;


    public Jugador() {
            Texture textura = new Texture("personaje/Spritesheet jugador.png");
            TextureRegion[][] temp = new TextureRegion(textura).split(32, 32);
            Texture texturaAtaque = new Texture("personaje/ataque spritesheet.png");
            TextureRegion[][] temp2 = new TextureRegion(texturaAtaque).split(33, 28);
            this.vida = 5;
            // Configurar animaciones
            TextureRegion[] framesCaminarDerecha = new TextureRegion[4];
            TextureRegion[] framesCaminarIzquierda= new TextureRegion[4];
            TextureRegion[] framesSaltar= new TextureRegion[4];
            TextureRegion[] framesAtacarDerecha = new TextureRegion[4];
            TextureRegion[] framesAtacarIzquierda = new TextureRegion[4];
            caminarDerecha = new Animation<>(0.1f, configuraranimacion(framesCaminarDerecha,2,temp));
            caminarIzquierda= new Animation<>(0.1f, configuraranimacion(framesCaminarIzquierda,3,temp));
            saltar= new Animation<>(0.3f, configuraranimacion(framesSaltar,4,temp));
            quieto = temp[0][0];
            atacarDerecha = new Animation<>(0.1f, configuraranimacion(framesAtacarDerecha,1,temp2));
            atacarIzquierda = new Animation<>(0.1f, configuraranimacion(framesAtacarIzquierda,2,temp2));

            // Configurar sprite
            sprite = new Sprite(quieto);
            sprite.setSize(80, 80);
            sprite.setPosition(0, 150);
            espada=new Espada();

        }

        public Rectangle getHitbox() {
            return new Rectangle(sprite.getX(), sprite.getY(), hitboxWidth, hitboxHeight);
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
                    mirandoDerecha = true;
                    nuevoX += 100 * delta;
                    if (!saltando && !cayendo) { // Solo usa la animación de caminar si está en el suelo
                        frame = caminarDerecha.getKeyFrame(estadoTiempo, true);
                        estadoTiempo += delta;
                    }

                    break;
                case IZQUIERDA:
                    mirandoDerecha = false;
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
                case ARRIBA_DERECHA:
                    mirandoDerecha = true;
                    if (!enElAire) {
                        enElAire = true;
                        velocidadY = velocidadSalto;
                    }
                    nuevoX += 100 * delta; // Movimiento hacia la derecha
                    frame = saltar.getKeyFrame(estadoTiempo, false); // Mantén la animación de salto
                    estadoTiempo += delta;
                    break;

                case ARRIBA_IZQUIERDA:
                    mirandoDerecha = false;
                    if (!enElAire) {
                        enElAire = true;
                        velocidadY = velocidadSalto;
                    }
                    nuevoX -= 100 * delta; // Movimiento hacia la izquierda
                    frame = saltar.getKeyFrame(estadoTiempo, false); // Mantén la animación de salto
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

        Rectangle rectJugador = getHitbox();
        rectJugador.setPosition(x,y);

        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                // Ajustar al borde superior de la plataforma
                return rect.getY() + rect.getHeight();
            }
        }
        return y;
    }

    private float ajustarALadoIzquierdo(float x, Array<Rectangle> colisionables) {

        Rectangle rectJugador = getHitbox();
        rectJugador.setX(x);

        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() - hitboxWidth;
            }
        }
        return x;
    }

    private float ajustarALadoDerecho(float x, Array<Rectangle> colisionables) {

        Rectangle rectJugador = getHitbox();
        rectJugador.setX(x);

        for (Rectangle rect : colisionables) {
            if (rectJugador.overlaps(rect)) {
                return rect.getX() + rect.getWidth();
            }
        }
        return x;
    }


    public boolean verificarColision(float x, float y, Array<Rectangle> colisionables) {

        // Crear el rectángulo de colisión con las dimensiones del hitbox
        Rectangle rectJugador = getHitbox();
        rectJugador.setPosition(x,y);

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

    public void recibirDanio(int cantidad) {
        if(!invulnerable) {
            vida -= cantidad;
            System.out.println("El jugador recibio daño");

            activarInvulnerabilidad(); // Activa la invulnerabilidad temporal después de recibir daño
        }else{
            System.out.println("El jugador es invunerable");
        }
    }

    private void activarInvulnerabilidad() {
        invulnerable = true;
        System.out.println("Se activo invulnerabilidad");
        tiempoInvulnerabilidad = 0f; // Reinicia el contador de invulnerabilidad
    }

    public void actualizar(float delta) {
        // Actualiza el temporizador de invulnerabilidad
        if (invulnerable) {
            tiempoInvulnerabilidad += delta;
            if (tiempoInvulnerabilidad >= DURACION_INVULNERABILIDAD) {
                invulnerable = false; // Termina la invulnerabilidad después de un tiempo
            }
        }
        atacar();
        espada.actualizarPosicion(sprite.getX(), sprite.getY(), mirandoDerecha);

        // Si el jugador está atacando, marcar la espada como atacando
        if (atacando) {
            espada.setAtacando(true);
        } else {
            espada.setAtacando(false);
        }

        // Opcional: Dibujar hitbox para debug
        if (espada.isAtacando()) {
            System.out.println("Hitbox Espada: " + espada.getHitbox());
        }
    }

    public void atacar() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.E) && !atacando) {
            atacando = true; // Inicia el ataque
            tiempoAtaque = 0f; // Reinicia el tiempo de ataque
        }

        if (atacando) {
            if (mirandoDerecha) {
                sprite.setRegion(atacarDerecha.getKeyFrame(tiempoAtaque, false));
            } else {
                sprite.setRegion(atacarIzquierda.getKeyFrame(tiempoAtaque, false));
            }

            tiempoAtaque += Gdx.graphics.getDeltaTime();

            // Verifica si la animación ha terminado
            if (atacarDerecha.isAnimationFinished(tiempoAtaque) || atacarIzquierda.isAnimationFinished(tiempoAtaque)) {
                atacando = false; // Termina el ataque
            }
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getPosicionX() {
        return getX();
    }

    public Animation<TextureRegion> getCaminarDerecha() {
        return caminarDerecha;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public int getVida() {
        return vida;
    }

    public Espada getEspada() {
        return espada;
    }
}
  

