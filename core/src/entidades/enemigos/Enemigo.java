package entidades.enemigos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import utiles.AnimacionUtiles;

public class Enemigo extends Actor {
    private final Sprite sprite;
    private final Animation<TextureRegion> caminar;
    private final float gravedad = -500f;
    private final float anchoHitbox;
    private final float altoHitbox;
    protected float estadoTiempo;
    private boolean enElAire;
    private int rangoDeteccion=100;
    private int rangoPatrulla;
    private float velocidad;
    private Estado estadoactual;
    private boolean moviendoDerecha;
    private float posicionInicialX;

    public Enemigo(String rutaCaminar, float x, float y, float anchoHitbox, float altoHitbox,int rangoPatrulla,float velocidad) {
        Texture textura = new Texture(rutaCaminar);
        sprite = new Sprite();
        caminar = AnimacionUtiles.crearAnimacion(textura, 16, 19, 4, 0, 0.5f);
        this.rangoPatrulla=rangoPatrulla;
        this.anchoHitbox = anchoHitbox;
        this.altoHitbox = altoHitbox;
        this.velocidad=velocidad;
        this.estadoactual = Estado.PATRULLANDO;
        this.moviendoDerecha = true;
        this.posicionInicialX=x;
        setPosition(x,y);
        setSize(40f, 40f);
    }

    public void actualizar(float delta, float jugadorX, float jugadorY, Array<Rectangle> colisionables) {
        estadoTiempo += delta;

        // Lógica de movimiento horizontal (ejemplo: perseguir al jugador)
        float distanciaAlJugador = Math.abs(jugadorX - getX());
        if (distanciaAlJugador <= rangoDeteccion) {
            estadoactual = Estado.PERSIGUIENDO;
        } else {
            estadoactual = Estado.PATRULLANDO;
        }

        // Ejecutar lógica según el estado
        if (estadoactual == Estado.PATRULLANDO) {
            patrullar(delta);
        } else if (estadoactual == Estado.PERSIGUIENDO) {
            perseguir(jugadorX, delta);
        }

        ajustarPosicion(colisionables);

    }
    private void patrullar(float delta) {
        if (moviendoDerecha) {
            setX(getX() + velocidad * delta);
            if (getX() > posicionInicialX + rangoPatrulla) {
                moviendoDerecha = false;
            }
        } else {
            setX(getX() - velocidad * delta);
            if (getX() < posicionInicialX) {
                moviendoDerecha = true;
            }
        }
    }

    private void perseguir(float jugadorX, float delta) {
        if (jugadorX > getX()) {
            setX(getX() + velocidad * delta);
        } else if (jugadorX < getX()) {
            setX(getX() - velocidad * delta);
        }
    }

    public void ajustarPosicion(Array<Rectangle> colisionables) {
        Rectangle hitbox = getHitbox();
        enElAire = true; // Por defecto, asumimos que está en el aire.

        // Aplicar gravedad si está en el aire.
        if (enElAire) {
            setY(getY() + gravedad * Gdx.graphics.getDeltaTime());
        }

        for (Rectangle colision : colisionables) {
            if (hitbox.overlaps(colision)) {
                // Resolver colisiones verticales
                if (hitbox.y > colision.y && hitbox.y + hitbox.height > colision.y + colision.height) {
                    // Golpe desde arriba (toca el suelo)
                    setY(colision.y + colision.height);
                    enElAire = false; // Está tocando el suelo, no está en el aire.
                } else if (hitbox.y + hitbox.height < colision.y) {
                    // Golpe desde abajo (toca el techo)
                    setY(colision.y - hitbox.height);
                }

                // Resolver colisiones horizontales
                if (hitbox.x < colision.x) { // Golpe desde la izquierda
                    setX(colision.x - hitbox.width);
                } else if (hitbox.x + hitbox.width > colision.x + colision.width) { // Golpe desde la derecha
                    setX(colision.x + colision.width);
                }
            }
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), anchoHitbox, altoHitbox);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion frame = caminar.getKeyFrame(estadoTiempo, true);
        sprite.setRegion(frame);
        batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
    }

}
