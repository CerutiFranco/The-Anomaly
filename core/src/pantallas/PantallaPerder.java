package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import elementos.Texto;
import entradas_salidas.Entradas;
import utiles.Render;

public class PantallaPerder implements Screen {
    private Texto texto;
    private Texto texto2;
    private Camara camaraJuego;
    private Entradas entradas=new Entradas();
    final int anchoPantalla = 426;
    final int altoPantalla = 520;
    private PantallaMenu pantallaMenu;

    public PantallaPerder(Camara camaraJuego) {
        this.camaraJuego = camaraJuego;
    }


    @Override
    public void show() {
        texto=new Texto();
        texto2=new Texto();
        Gdx.input.setInputProcessor(entradas);

    }

    @Override
    public void render(float v) {
        Render.batch.begin();
        Render.batch.setColor(1, 1, 1, 0f);
        float centroX = camaraJuego.getCamara().position.x - anchoPantalla / 2f;
        float centroY = camaraJuego.getCamara().position.y - altoPantalla / 2f;
        texto.drawCenteredText("HAS MUERTO",centroX, centroY + 450,anchoPantalla);
        texto2.drawCenteredText("Presione Enter para Volver al Menu",centroX, centroY + 400,anchoPantalla);
        texto.setColor(Color.WHITE);
        texto2.setColor(Color.WHITE);
        Render.batch.setColor(1, 1, 1, 1);
        Render.batch.end();
        if (entradas.isEnter()) {
            Render.cambiarPantalla( pantallaMenu= new PantallaMenu());
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
