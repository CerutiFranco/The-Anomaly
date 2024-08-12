package elementos;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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
	}
	
	public Mapa(String ruta) {
		this(ruta, 1.0f);
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
	

	public void ObtenerDimensiones() {
		int mapWidthInTiles = mapaTiled.getProperties().get("width", Integer.class);
		int mapHeightInTiles = mapaTiled.getProperties().get("height", Integer.class);
		int tileWidth = mapaTiled.getProperties().get("tilewidth", Integer.class);
		int tileHeight = mapaTiled.getProperties().get("tileheight", Integer.class);

		this.width = mapWidthInTiles * this.escalaMapa * tileWidth;
		this.height = mapHeightInTiles * this.escalaMapa * tileHeight;
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
