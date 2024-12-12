package camaras;

public enum PosicionesCamara {
    POSICION1(640),
    POSICION2(1920),
    POSICION3(3200),
    POSICION4(4480),
    POSICION5(5760);
    private float cooredenadasX;
    private float coordenadasY;



    PosicionesCamara(int cooredenadasX) {
        this.cooredenadasX = cooredenadasX;
        this.coordenadasY=320;

    }

    public float getCooredenadasX() {
        return cooredenadasX;
    }

    public float getCoordenadasY() {
        return coordenadasY;
    }
}
