package entradas_salidas;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Entradas implements InputProcessor {

	private boolean abajo = false, arriba = false, enter = false, derecha = false, izquierda = false;

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub

		if (keycode == Keys.DOWN) {
			abajo = true;
		}
		if (keycode == Keys.UP) {
			arriba = true;
		}
		if (keycode == Keys.ENTER) {
			enter = true;
		}
		if (keycode == Keys.RIGHT) {
			derecha = true;
		}
		if (keycode == Keys.LEFT) {
			izquierda = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.DOWN) {
			abajo = false;
		}
		if (keycode == Keys.UP) {
			arriba = false;
		}
		if (keycode == Keys.ENTER) {
			enter = false;
		}
		if (keycode == Keys.RIGHT) {
			derecha = false;
		}
		if (keycode == Keys.LEFT) {
			izquierda = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}

	public boolean isEnter() {
		return enter;
	}

	public boolean isDerecha() {
		return derecha;
	}

	public boolean isIzquierda() {
		return izquierda;
	}
	
	public Direcciones getDireccion() {
		if(isAbajo()) return Direcciones.ABAJO;
		if(isArriba()) return Direcciones.ARRIBA;
		if(isDerecha()) return Direcciones.DERECHA;
		if(isIzquierda()) return Direcciones.IZQUIERDA;
		return Direcciones.QUIETO;
	}
}
