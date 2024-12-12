package camaras;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

    public class Camara {
        private OrthographicCamera camara;
        private Viewport viewport;

        private static final int TILE_SIZE = 32; // Tamaño de cada tile en píxeles
        private static final int SCENE_WIDTH_TILES = 40; // Ancho de la escena en tiles
        private static final int SCENE_HEIGHT_TILES = 20; // Alto de la escena en tiles
        private  float viewportWidth;
        private  float viewportHeight;
        private int posicionActual = 0;

        public Camara() {
            // Configurar la cámara
            camara = new OrthographicCamera();
            viewportWidth = SCENE_WIDTH_TILES * TILE_SIZE;
            viewportHeight = SCENE_HEIGHT_TILES * TILE_SIZE;

            // Usar FitViewport para manejar la relación de aspecto
            viewport = new StretchViewport(viewportWidth, viewportHeight, camara);


            // Posicionar la cámara en la primera escena
            posicionarCamara(PosicionesCamara.POSICION1);
        }
        public void actualizar() {
            camara.update();
        }

        public OrthographicCamera getCamara() {
            return camara;
        }

        public Viewport getViewport() {
            return viewport;
        }

        public void resize(int width, int height) {
            viewport.update(width, height);
        }

        public void posicionarCamara(PosicionesCamara posicion) {
            camara.position.set(posicion.getCooredenadasX(), posicion.getCoordenadasY(), 0);
            camara.update();
        }
        public void moverACamaraSiguiente() {
            if (posicionActual < PosicionesCamara.values().length - 1) {
                posicionActual++;
                posicionarCamara(PosicionesCamara.values()[posicionActual]);
            }
        }

        public float getViewportWidth() {
            return viewportWidth;
        }

        public float getViewportHeight() {
            return viewportHeight;
        }
    }
