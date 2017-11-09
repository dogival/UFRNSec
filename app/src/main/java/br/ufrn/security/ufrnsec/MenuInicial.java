package br.ufrn.security.ufrnsec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.widget.*;
import android.view.*;

public class MenuInicial extends AppCompatActivity {

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
    }
}
