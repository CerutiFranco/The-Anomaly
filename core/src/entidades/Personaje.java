package entidades;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Personaje extends Actor {
	protected Animation<TextureRegion> animacionActual; // Animación que se está ejecutando
	protected float tiempoEstado; // Tiempo acumulado para animaciones
	protected Vector2 velocidad; // Para movimiento
	protected boolean enElSuelo;

	public Personaje() {
		tiempoEstado = 0f;
		velocidad = new Vector2(0, 0);
		enElSuelo = true;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		tiempoEstado += delta;

		// Actualizar posición con base en la velocidad
		moveBy(velocidad.x * delta, velocidad.y * delta);

		// Aplicar gravedad (si es necesario)
		if (!enElSuelo) {
			velocidad.y -= 981 * delta; // Gravedad aproximada
		}

		// Evitar que caiga más allá del suelo
		if (getY() <= 0) {
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

}
