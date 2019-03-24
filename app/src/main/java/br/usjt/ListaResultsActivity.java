package br.usjt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaResultsActivity extends AppCompatActivity {

    private static final String TAG = "erro-android";
    private ListView gpsListView;
    private String locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_results);
        Log.i(TAG,"onCreate-ListaResultsActivity");
        gpsListView = findViewById(R.id.gpsListView);
        Intent origemIntent = getIntent();
        String location = origemIntent.getStringExtra("location");
        locations = location;
        final List<String> locations = busca(location);
        ArrayAdapter <String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        gpsListView.setAdapter(adapter);
    }

    public List<String> busca (String location){
        List<String> locations = geraListaGps();
        if(location == null || location.length() == 0){
            return locations;
        }
        List <String> resultado = new ArrayList<>();
        for(String localizacao : locations){
            if(localizacao.toLowerCase().contains(location.toLowerCase())){
                resultado.add(localizacao);
            }
        }
        return resultado;
    }

    public ArrayList<String> geraListaGps(){
        Log.i(TAG,"geraListaErro");
        ArrayList<String> lista = new ArrayList<>();
        for(int cont = 0 ; cont <= 50 ; cont++) {
            lista.add(locations);
        }
        return lista;
    }
}
