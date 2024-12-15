package pantallas;

import camaras.Camara;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import elementos.Imagen;
import elementos.Texto;
import entradas_salidas.Entradas;
import utiles.AnimacionUtiles;
import utiles.Render;

public class PantallaOpciones implements Screen {
    String texto1="Musica";
    String texto2="Volver";
    TextureRegion[] framesMusica;
    Texture textura;
    final int anchoPantalla = 426;
    final int altoPantalla = 300;
    private Texto[] textos = new Texto[3];
    private Camara camaraJuego;
    private  PantallaPausa pantallaPausa;
    Imagen fondo;
    private float anchoSprite=95;
    private float altoSprite=28;
    private Entradas entradas=new Entradas();
    public PantallaOpciones(PantallaPausa pantallaPausa, Camara camaraJuego) {
        this.pantallaPausa = pantallaPausa;
        this.camaraJuego = camaraJuego;
        this.textura= new Texture("barras/barras musica.png");

    }
    @Override
    public void show() {
        fondo = new Imagen("fondos/fondoopciones.png");
        for (int i = 0; i < textos.length; i++) {
            textos[i] = new Texto();
        }
        fondo.setSize(anchoPantalla, altoPantalla);
        framesMusica=AnimacionUtiles.cargarTextureRegion(framesMusica,textura,95,28,6);
        Gdx.input.setInputProcessor(entradas);
    }

    @Override
    public void render(float v) {
        Render.batch.begin();
        float centroX = camaraJuego.getCamara().position.x - anchoPantalla / 2f;
        float centroY = camaraJuego.getCamara().position.y - altoPantalla / 2f;
        fondo.setPosition(centroX, centroY);
        fondo.dibujar();
        textos[0].drawCenteredText(texto1,centroX-100, centroY + 250,anchoPantalla);
        textos[1].drawCenteredText(texto2,centroX-100, centroY+200,anchoPantalla);

        dibujarBarraMusica(entradas,framesMusica[0]);
        Render.batch.end();
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
    public void dibujarBarraMusica(Entradas entradas,TextureRegion frame){
        int i=0;

        //if(entradas.isDerecha()){
            Render.batch.draw(frame,100,100,anchoSprite,altoSprite);
       //}

    }
}
