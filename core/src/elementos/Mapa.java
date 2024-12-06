package elementos;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.MapLayer;

public class Mapa {
	private float height;
	private float width;
	private float escalaMapa;
	private TiledMap mapaTiled;
	private String ruta;
	private TmxMapLoader mapLoader;

	public Mapa(String ruta, float escalaMapa) {
		this.escalaMapa = escalaMapa;
		this.ruta = ruta;
		this.mapLoader = new TmxMapLoader();
		this.mapaTiled = cargarMapa();
		cargasCapas();
	}

	public Mapa(String ruta) {
		this(ruta, 1.0f);
	}

	private void cargasCapas() {
		for (MapLayer layer : this.mapaTiled.getLayers()) {
			if (layer instanceof TiledMapTileLayer) {
				TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
				procesarCapa(tileLayer);
			}
		}
	}

	private void procesarCapa(TiledMapTileLayer layer) {
		float layerWidth = layer.getWidth() * layer.getTileWidth() * this.escalaMapa;
		float layerHeight = layer.getHeight() * layer.getTileHeight() * this.escalaMapa;
		System.out.println("Procesando capa: " + layer.getName());
		System.out.println("Dimensiones: " + layerWidth + " x " + layerHeight);

		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell != null && cell.getTile() != null) {
					boolean isBlocked = cell.getTile().getProperties().containsKey("blocked");
					if (isBlocked) {
						System.out.println("Tile bloqueado en [" + x + ", " + y + "]");
					}
				}
			}
		}
	}



	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public float setEscalaMapa(float escalaMapa) {
		return this.escalaMapa = escalaMapa;
	}
	
	public float getEscalaMapa() {
		return this.escalaMapa;
	}

	public TiledMap cargarMapa() {
		try {
			return this.mapLoader.load(ruta);
		} catch (Exception e) {
			System.out.println("Ocurrio un error");
			return null;
		}
		
	}

	public TiledMap getTiled() {
		return this.mapaTiled;
	}

}
