package br.ufrn.security.ufrnsec;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.util.Log;
import android.widget.*;
import android.view.*;

public class MenuInicial extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        Button botaoEmergencia = (Button) findViewById(R.id.emergencia);

        botaoEmergencia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(MenuInicial.this, EmergenciaActivity.class);
                startActivity(it);
            }
        });

        Button botaoChamarSeg = (Button) findViewById(R.id.chamarSeguranca);

        botaoChamarSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    } else {
                        ActivityCompat.requestPermissions(MenuInicial.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        startActivity(callIntent);
                    }
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Chamando a segurança", "Ligação falhou", activityException);
                }
            }
        });
    }
}
