package br.ufrn.security.ufrnsec;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button botaoRoubo = (Button) findViewById(R.id.buttonRoubo);

        botaoRoubo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(EmergenciaActivity.this, RouboActivity.class);
                startActivity(it);
            }
        });
        if(ContextCompat.checkSelfPermission(EmergenciaActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EmergenciaActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setMessage("Necessário permissão para fazer salvar arquivos!");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(EmergenciaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
                }
            });
        }
        ActivityCompat.requestPermissions(EmergenciaActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                99);

    }
}
