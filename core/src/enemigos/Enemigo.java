package enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemigo {
    protected Sprite sprite;
    protected Rectangle hitbox;

    public Enemigo(Sprite sprite, float anchoHitbox, float altoHitbox) {
        this.sprite = sprite;
        this.hitbox = new Rectangle(
                sprite.getX(),
                sprite.getY(),
                anchoHitbox,
                altoHitbox
        );
    }

    public void mover(float deltaX, float deltaY) {
        sprite.translate(deltaX, deltaY);
        hitbox.setPosition(sprite.getX(), sprite.getY());
    }

    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void actualizar(float delta) {

    }
}

