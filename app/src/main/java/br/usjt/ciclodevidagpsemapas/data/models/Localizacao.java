package br.usjt.ciclodevidagpsemapas.data.models;

import java.io.Serializable;

public class Localizacao implements Serializable {
    public double latitude;
    public double longitude;
    public Clima clima;

    public Localizacao(double latitude, double longitude, Clima clima) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.clima = clima;
    }
}
