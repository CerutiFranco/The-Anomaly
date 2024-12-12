package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import elementos.Imagen;
import elementos.Texto;
import entradas_salidas.Entradas;
import utiles.Render;

public class PantallaPausa implements Screen {
    private PantallaJuego pantallaJuego;
    private Camara camaraJuego;
    public PantallaPausa(PantallaJuego pantallaJuego, Camara camaraJuego) {
        this.pantallaJuego = pantallaJuego;
        this.camaraJuego = camaraJuego;
    }
    Imagen fondo;

    final int anchoPantalla = 426;
    final int altoPantalla = 520;

    Entradas entradas = new Entradas();
    int opc = 1;
    String texto1 = "Reanudar";
    String texto2 = "Opciones";
    String texto3 = "Salir";
    private Texto[] textos = new Texto[3];
    public float tiempo = 0;
    @Override
    public void show() {
        fondo = new Imagen("selva.jpg");
        fondo.setSize(anchoPantalla, altoPantalla);
        for (int i = 0; i < textos.length; i++) {
            textos[i] = new Texto();
        }

        Gdx.input.setInputProcessor(entradas);
    }

    @Override
    public void render(float delta) {
        Render.batch.begin();
        Render.batch.setColor(1, 1, 1, 0f);
        float centroX = camaraJuego.getCamara().position.x - fondo.getAncho() / 2f;
        float centroY = camaraJuego.getCamara().position.y - fondo.getAlto() / 2f;
        fondo.setPosition(centroX-100, centroY-100);
        fondo.dibujar();

        textos[0].drawCenteredText(texto1,centroX, centroY + 200,fondo.getAncho());
        textos[1].drawCenteredText(texto2,centroX, centroY+150,fondo.getAncho());
        textos[2].drawCenteredText(texto3,centroX, centroY+100, fondo.getAncho());
        Render.batch.setColor(1, 1, 1, 1);
        Render.batch.end();

        tiempo += delta;
        if (entradas.isAbajo()) {
            if (tiempo > 0.2f) {
                tiempo = 0;
                opc++;
                if (opc > textos.length) {
                    opc = 1;
                }
            }
        }
        if (entradas.isArriba()) {
            if (tiempo > 0.2f) {
                tiempo = 0;
                opc--;
                if (opc < 1) {
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
        switch (opc) {
            case 1:
                if (entradas.isEnter()) {
                    Render.cambiarPantalla(pantallaJuego);
                }
                break;
            case 2:
                break;
            case 3:
                if (entradas.isEnter()) {
                    pantallaJuego.dispose(); // Libera recursos de PantallaJuego
                    this.dispose();
                    Render.cambiarPantalla(new PantallaMenu()); // Cambia a la pantalla de menú principal
                }
                break;
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Render.cambiarPantalla(pantallaJuego); // Cambiar de nuevo a PantallaJuego
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
