package br.ufrn.security.ufrnsec;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.*;
import android.util.Log;
import android.widget.*;
import android.view.*;

public class MenuInicial extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 99;
    LocationManager locationManager;
    double longitude, latitude;

    public void funcContato(View view){
        Intent it = new Intent(MenuInicial.this, contatoActivity.class);
        startActivity(it);
    }

    public void funcDicas(View view){
        Intent it = new Intent(MenuInicial.this, dicasActivity.class);
        startActivity(it);
    }

    public void chamar(View view) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:84988175658"));
            if (ContextCompat.checkSelfPermission(MenuInicial.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MenuInicial.this);
                alertBuilder.setCancelable(true);
                alertBuilder.setMessage("Necessário permissão para fazer ligações!");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MenuInicial.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                });
            }

            ActivityCompat.requestPermissions(MenuInicial.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            startActivity(callIntent);

        } catch (ActivityNotFoundException activityException) {
            Log.e("Chamando a segurança", "Ligação falhou", activityException);
        }
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Habilitar Localização")
                .setMessage("Suas configurações de localização estão desabilitadas.\nPor favor ative a localização para" +
                        "usar este aplicativo")
                .setPositiveButton("Configurações de Localização", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private final LocationListener locationListenerBest = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MenuInicial.this, "Usuário localizado", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        ImageButton botaoEmergencia = (ImageButton) findViewById(R.id.emergencia);

        botaoEmergencia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                checkLocation();
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String provider = locationManager.getBestProvider(criteria, true);
                if(provider != null) {
                    try {
                        locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerBest);
                    } catch (SecurityException se) {};
                }

                Intent it = new Intent(MenuInicial.this, EmergenciaActivity.class);
                startActivity(it);
            }
        });



    }
}
