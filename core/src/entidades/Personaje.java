package entidades;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Personaje extends Actor {
	protected Animation<TextureRegion> animacionActual; // Animación que se está ejecutando
	protected float tiempoEstado; // Tiempo acumulado para animaciones
	protected Vector2 velocidad; // Para movimiento
	protected boolean enElSuelo;

	protected Rectangle hitbox;

	public Personaje() {
		tiempoEstado = 0f;
		velocidad = new Vector2(0, 0);
		enElSuelo = true;

		hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		tiempoEstado += delta;

		float newX = getX() + velocidad.x * delta;
		float newY = getY() + velocidad.y * delta;

		// Actualizar posición con base en la velocidad
		//moveBy(velocidad.x * delta, velocidad.y * delta);

		// Aplicar gravedad (si es necesario)
		if (!enElSuelo) {
			velocidad.y -= 981 * delta; // Gravedad aproximada
		}

		// Evitar que caiga más allá del suelo
		if (newY <= 0) {
			setY(0);
			enElSuelo = true;
			velocidad.y = 0;
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (animacionActual != null) {
			TextureRegion frame = animacionActual.getKeyFrame(tiempoEstado, true);
			batch.draw(frame, getX(), getY(), getWidth(), getHeight());
		}
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public boolean verificarColision(Rectangle rectanguloColision) {
		return hitbox.overlaps(rectanguloColision);
	}

	private void actualizarHitBox() {
		hitbox.setPosition(getX(), getY());
		hitbox.setSize(getWidth(), getHeight());
	}

	public void detener() {
		velocidad.set(0, 0); // Detiene cualquier movimiento
		actualizarHitBox();  // Actualiza el hitbox a la posición actual
	}

}
