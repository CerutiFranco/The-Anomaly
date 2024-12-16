package armas;

import com.badlogic.gdx.math.Rectangle;

public class Espada {
    private Rectangle hitbox;
    private float anchoHitbox = 42.5f;
    private float altoHitbox = 67.5f;
    private boolean atacando = false;

    public Espada() {
        hitbox = new Rectangle(0, 0, anchoHitbox, altoHitbox);
    }

    public void actualizarPosicion(float xJugador, float yJugador, boolean mirandoDerecha) {
        // Posicionar la hitbox a la derecha o izquierda del jugador
        if (mirandoDerecha) {
            hitbox.setPosition(xJugador + anchoHitbox, yJugador); // Derecha del jugador
        } else {
            hitbox.setPosition(xJugador - anchoHitbox, yJugador); // Izquierda del jugador
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public boolean isAtacando() {
        return atacando;
    }
}
