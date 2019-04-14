package br.usjt.ciclodevidagpsemapas.data;

import java.io.Serializable;
import java.util.List;

public class DataSource implements Serializable {
    public List<Localizacao> localizacoes;

    public DataSource(List<Localizacao> localizacoes){
        this.localizacoes = localizacoes;
    }
}
