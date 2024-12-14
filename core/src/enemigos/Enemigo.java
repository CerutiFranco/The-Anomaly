package enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Enemigo {

    protected Rectangle hitbox;
    protected float velocidadX;
    protected float x, y;
    protected float estadoTiempo;
    protected Animation<TextureRegion> caminar;
    protected Estados estadoActual;
    protected float rangoPersecucion;
    protected float rangoPatrulla; // Distancia máxima de patrulla desde el punto inicial
    protected float xInicial; // Posición inicial para controlar el rango de patrulla
    protected boolean moviendoDerecha;

    public Enemigo( float x,float y,float anchoHitbox, float altoHitbox,float rangoPersecucion) {
        this.hitbox = new Rectangle(
                x,
                y,
                anchoHitbox,
                altoHitbox
        );
        this.velocidadX=100;
        this.estadoTiempo=0;
        this.estadoActual = Estados.PATRULLANDO;
        this.rangoPersecucion=rangoPersecucion;
        this.xInicial = x;
        this.moviendoDerecha = true;
        this.rangoPatrulla=100;

    }

    public void actualizar(float delta, float jugadorX, float jugadorY) {
        // Calcular distancia al jugador
        float distanciaX = jugadorX - x;
        float distanciaY = jugadorY - y;
        float distancia = (float) Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

        // Cambiar estado según la distancia al jugador
        if (distancia <= rangoPersecucion) {
            estadoActual = Estados.PERSIGUIENDO;
        } else {
            estadoActual = Estados.PATRULLANDO;
        }

        // Actualizar lógica según el estado
        switch (estadoActual) {
            case PERSIGUIENDO:
                // Mover hacia el jugador
                float direccionX = distanciaX > 0 ? 1 : -1;
                x += direccionX * velocidadX * delta;

                // Actualizar hitbox
                hitbox.setPosition(x, y);

                // Incrementar tiempo para la animación
                estadoTiempo += delta;
                break;

            case PATRULLANDO:
                // Movimiento dentro del rango de patrulla
                if (moviendoDerecha) {
                    x += velocidadX * delta;
                    if (x >= xInicial + rangoPatrulla) {
                        moviendoDerecha = false;
                    }
                } else {
                    x -= velocidadX * delta;
                    if (x <= xInicial - rangoPatrulla) {
                        moviendoDerecha = true;
                    }
                }

                // Actualizar hitbox
                hitbox.setPosition(x, y);

                // Incrementar tiempo para la animación
                estadoTiempo += delta;
                break;
        }
    }

    public void dibujar(SpriteBatch batch) {
        TextureRegion frameActual = caminar.getKeyFrame(estadoTiempo, true);
        batch.draw(frameActual, x, y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public void ajustarPosicion(Array<Rectangle> colisionables) {
        for (Rectangle rect : colisionables) {
            if (hitbox.overlaps(rect)) {
                // Mover fuera en el eje Y (por encima del rectángulo)
                if (y < rect.getY() + rect.getHeight()) {
                    y = rect.getY() + rect.getHeight();
                }
                // Mover fuera en el eje X (al lado derecho del rectángulo)
                if (x < rect.getX()) {
                    x = rect.getX() - hitbox.getWidth();
                } else if (x > rect.getX() + rect.getWidth()) {
                    x = rect.getX() + rect.getWidth();
                }
                hitbox.setPosition(x, y);
                break;
            }
        }
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setPosition(x, y); // Actualizar también la posición del hitbox
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
