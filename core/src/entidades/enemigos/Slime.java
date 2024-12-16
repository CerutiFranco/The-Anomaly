package entidades.enemigos;

public class Slime extends Enemigo{
    public Slime(float x, float y, int rangoPatrulla) {
        super("enemigos/animacion slime.png", x, y,16,19,rangoPatrulla,100,3);
    }
}
