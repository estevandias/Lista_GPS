package br.usjt.ciclodevidagpsemapas.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import br.usjt.ciclodevidagpsemapas.R;
import br.usjt.ciclodevidagpsemapas.data.db.LocalizacaoDAO;
import br.usjt.ciclodevidagpsemapas.data.models.Localizacao;

public class LocalizacoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacoes);

        List<Localizacao> localizacoes = new LocalizacaoDAO(this).recuperaLocalizacoes();

        RecyclerView localizacoesRecyclerView = findViewById(R.id.localizacoes_list);
        localizacoesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        localizacoesRecyclerView.setNestedScrollingEnabled(false);
        localizacoesRecyclerView.setAdapter(new LocalizacoesAdapter(localizacoes));
    }
}
