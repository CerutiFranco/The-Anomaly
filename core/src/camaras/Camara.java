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

        public Camara() {
            // Configurar la cámara
            camara = new OrthographicCamera();
            float viewportWidth = SCENE_WIDTH_TILES * TILE_SIZE;
            float viewportHeight = SCENE_HEIGHT_TILES * TILE_SIZE;

            // Usar FitViewport para manejar la relación de aspecto
            viewport = new StretchViewport(viewportWidth, viewportHeight, camara);

            // Posicionar la cámara en la primera escena
            camara.position.set(viewportWidth / 2, viewportHeight / 2, 0);
            camara.update();
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
}
