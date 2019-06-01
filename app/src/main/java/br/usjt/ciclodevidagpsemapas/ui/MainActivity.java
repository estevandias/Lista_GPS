package br.usjt.ciclodevidagpsemapas.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.usjt.ciclodevidagpsemapas.R;
import br.usjt.ciclodevidagpsemapas.data.db.LocalizacaoDAO;
import br.usjt.ciclodevidagpsemapas.data.models.Localizacao;
import br.usjt.ciclodevidagpsemapas.data.models.Clima;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_GPS = 1001;

    private TextView locationText;

    private LocalizacaoDAO localizacaoDAO;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = findViewById(R.id.locationText);

        localizacaoDAO = new LocalizacaoDAO(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onLocationChanged(Location location) {
                double latitudeAtual = location.getLatitude();
                double longitudeAtual = location.getLongitude();

                locationText.setText(String.format(getString(R.string.main_label), latitudeAtual, longitudeAtual));
                new TaskObtemPrevisoes().execute(latitudeAtual, longitudeAtual);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };

        setSupportActionBar(findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener((view) -> chamaLocalizacoesActivity());
    }

    private void chamaLocalizacoesActivity() {
        startActivity(new Intent(this, LocalizacoesActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean gpsPermissionGranted =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (gpsPermissionGranted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000 * 60, 200, locationListener);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean gpsPermissionGranted = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (requestCode == REQUEST_LOCATION_GPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (gpsPermissionGranted) locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000 * 60, 200, locationListener);
            } else {
                Toast.makeText(this, getString(R.string.gps_disabled), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @SuppressLint("StaticFieldLeak")
    private class TaskObtemPrevisoes extends AsyncTask<Double, Void, Void> {

        @Override
        protected Void doInBackground(Double... endereco) {
            try {
                double latitude = endereco[0];
                double longitude = endereco[1];
                URL url = new URL(getString(R.string.service_url, latitude, longitude));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder resultado = new StringBuilder();
                String aux;

                while ((aux = reader.readLine()) != null) {
                    resultado.append(aux);
                }

                localizacaoDAO.insereLocalizacao(new Localizacao(latitude, latitude, lidaComJSON(resultado.toString())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private Clima lidaComJSON(String json) throws JSONException {
            JSONArray linhas = new JSONObject(json).getJSONArray("list");
            JSONObject dia = linhas.getJSONObject(0);
            JSONObject main = dia.getJSONObject("main");
            JSONObject tempo = dia.getJSONArray("weather").getJSONObject(0);
            return new Clima(dia.getLong("dt"), main.getDouble("temp_min"), main.getDouble("temp_max"), main.getDouble("humidity"), tempo.getString("description"));
        }
    }
}
