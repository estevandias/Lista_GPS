package br.usjt.ciclodevidagpsemapas.data;

import java.io.Serializable;

public class Localizacao implements Serializable {
    public double latitude;
    public double longitude;

    public Localizacao(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
