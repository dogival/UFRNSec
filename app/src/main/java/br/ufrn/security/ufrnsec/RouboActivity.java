package br.ufrn.security.ufrnsec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RouboActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roubo);

        final EditText descricaoOcorrido = (EditText) findViewById(R.id.descricaoText);
        String descricao = descricaoOcorrido.getText().toString();

        final EditText tempoOcorrido = (EditText) findViewById(R.id.tempoText);
        String tempo = tempoOcorrido.getText().toString();
    }
}
