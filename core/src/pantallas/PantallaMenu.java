package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Gdx;



import elementos.Imagen;
import elementos.Texto;
import entradas_salidas.Entradas;
import utiles.Render;

public class PantallaMenu implements Screen{

	Imagen fondo;
	BitmapFont fuente;
	GlyphLayout glyphlayout;
	final int anchoPantalla =1280;
	final int altoPantalla=720;
	
	Entradas entradas = new Entradas();
	int opc = 1;
	String texto1 = "Comenzar partida";
	String texto2 = "Opciones";
	String texto3 = "Salir";
	private Texto[] textos = new Texto[3];
	public float tiempo=0;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		fondo= new Imagen("selva.jpg");
		fondo.setSize(anchoPantalla, altoPantalla);
		for (int i = 0; i < textos.length; i++) {
            textos[i] = new Texto();
        }
		
		Gdx.input.setInputProcessor(entradas);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
		fondo.Dibujar();

		textos[0].drawCenteredText(texto1, altoPantalla / 2 + 100);
		textos[1].drawCenteredText(texto2, altoPantalla / 2 + 50);
		textos[2].drawCenteredText(texto3, altoPantalla / 2 );
		Render.batch.end();
		
		tiempo += delta;
		if(entradas.isAbajo()) {
			if(tiempo > 0.2f) {
				tiempo = 0;
				opc++;
				if(opc>textos.length) {
					opc = 1;
				}
			}
		}
		if(entradas.isArriba()) {
			if(tiempo>0.2f) {
				tiempo = 0;
				opc--;
				if(opc < 1) {
					opc = textos.length;
				}
			}
		}
			
		for (int i = 0; i < textos.length; i++) {
            if (i == opc - 1) {
                textos[i].setColor(Color.SKY);
            } else {
                textos[i].setColor(Color.WHITE);
            }
        }
		switch(opc) {
		case 1:
			if(entradas.isEnter()) {
				Render.app.setScreen(new PantallaJuego());
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
