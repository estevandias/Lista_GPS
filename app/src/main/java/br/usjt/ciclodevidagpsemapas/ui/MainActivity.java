package br.usjt.ciclodevidagpsemapas.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.usjt.ciclodevidagpsemapas.R;
import br.usjt.ciclodevidagpsemapas.data.db.LocalizacaoDAO;
import br.usjt.ciclodevidagpsemapas.data.models.Localizacao;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_GPS = 1001;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView locationText;

    private LocalizacaoDAO localizacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localizacaoDAO = new LocalizacaoDAO(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onLocationChanged(Location location) {
                double latitudeAtual = location.getLatitude();
                double longitudeAtual = location.getLongitude();

                locationText.setText(String.format(getString(R.string.main_label), latitudeAtual, longitudeAtual));
                localizacaoDAO.insereLocalizacao(new Localizacao(latitudeAtual, longitudeAtual));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        locationText = findViewById(R.id.locationText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaLocalizacoesActivity();
            }
        });
    }

    private void chamaLocalizacoesActivity() {
        Intent intent = new Intent(this, LocalizacoesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

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
                if (gpsPermissionGranted) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000 * 60, 200, locationListener);
                }
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
}
