package br.ufrn.security.ufrnsec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);

        Button botaoRoubo = (Button) findViewById(R.id.buttonRoubo);

        botaoRoubo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(EmergenciaActivity.this, RouboActivity.class);
                startActivity(it);
            }
        });


    }
}
