package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import elementos.Imagen;

public class Jugador extends Actor{
	private TextureAtlas atlas;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private TextureRegion currentFrame;
    public Jugador(){
    	atlas = new TextureAtlas(Gdx.files.internal("ruta/a/sprite_sheet.atlas"));
        Array<AtlasRegion> frames = atlas.findRegions("nombre_de_la_region");
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0f;
        
    }
    public void dispose() {
    	
    }
}
