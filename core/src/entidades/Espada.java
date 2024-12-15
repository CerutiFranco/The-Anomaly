package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Espada {
    private Sprite sprite;
    private float width = 40f;
    private float height = 60f;
    private float x = 0;
    private float y = 0;
    private float hitboxWidth = 35.5f;
    private float hitboxHeight = 60.5f;
    private ShapeRenderer shapeRenderer;

    public Espada() {
        Texture texture = new Texture("espada/Kaskara.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x,y);
        shapeRenderer = new ShapeRenderer();
    }

    public void actualizarPosicion(float jugadorX, float jugadorY, float jugadorWidth, float jugadorHeight) {
        float offsetX = jugadorWidth;
        float offsetY = jugadorHeight / 4.5f;

        float nuevoX = jugadorX + offsetX;
        float nuevoY = jugadorY + offsetY;

        sprite.setPosition(nuevoX, nuevoY);

        this.x = nuevoX;
        this.y = nuevoY;

        actualizarRectangulo();
        //System.out.println("Sprite X: " + sprite.getX() + ", Sprite Y: " + sprite.getY());
        //System.out.println("Hitbox X: " + getHitbox().x + ", Hitbox Y: " + getHitbox().y);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getHitbox(){
        return new Rectangle(sprite.getX(),sprite.getY(),hitboxWidth,hitboxHeight);
    }


    public void dispose() {
        sprite.getTexture().dispose();
        shapeRenderer.dispose();
    }

    public void actualizarRectangulo() {
        getHitbox().setPosition(x, y); // Sincroniza la posición del rectángulo
        getHitbox().setSize(sprite.getWidth(), sprite.getHeight()); // Sincroniza tamaño
    }
}
