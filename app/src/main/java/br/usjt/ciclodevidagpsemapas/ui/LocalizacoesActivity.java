package br.usjt.ciclodevidagpsemapas.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import br.usjt.ciclodevidagpsemapas.R;
import br.usjt.ciclodevidagpsemapas.data.DataSource;

public class LocalizacoesActivity extends AppCompatActivity {

    private static final String INTENT_EXTRA = "intent_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacoes);

        DataSource dataSource = (DataSource) getIntent().getSerializableExtra(INTENT_EXTRA);

        RecyclerView localizacoesRecyclerView = findViewById(R.id.localizacoes_list);
        localizacoesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        localizacoesRecyclerView.setNestedScrollingEnabled(false);
        localizacoesRecyclerView.setAdapter(new LocalizacoesAdapter(dataSource.localizacoes));
    }

    public static void chamaActivity(Context context, DataSource dataSource) {
        Intent intent = new Intent(context, LocalizacoesActivity.class);
        intent.putExtra(INTENT_EXTRA, dataSource);
        context.startActivity(intent);
    }
}
