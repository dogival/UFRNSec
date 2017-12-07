package br.ufrn.security.ufrnsec;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.TimeZone;

public class RouboActivity extends AppCompatActivity {

    String descricao;
    String tempo;
    private static final String TAG = "MEDIA";
    TimeZone tz = TimeZone.getTimeZone("GMT-3:00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roubo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //final EditText descricaoOcorrido = (EditText) findViewById(R.id.descricaoText);
        //descricao = descricaoOcorrido.getText().toString();

        // EditText tempoOcorrido = (EditText) findViewById(R.id.tempoText);
        //tempo = tempoOcorrido.getText().toString();
        checkExternalMedia();

    }

    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        Toast.makeText(RouboActivity.this,"\n\nExternal Media: readable="
                +mExternalStorageAvailable+" writable="+mExternalStorageWriteable,Toast.LENGTH_SHORT).show();
    }

    public void onSubmmitClick(View view){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/UFRNSec Logs");
        dir.mkdirs();
        //String filearq = "Log"+Calendar.getInstance().toString();
        File file = new File(dir, "LOG.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            EditText descricaoOcorrido = (EditText) findViewById(R.id.descricaoText);
            descricao = descricaoOcorrido.getText().toString();
            System.out.println(descricao);

            EditText tempoOcorrido = (EditText) findViewById(R.id.tempoText);
            tempo = tempoOcorrido.getText().toString();

            pw.println(descricao);
            pw.println(tempo);
            pw.flush();
            pw.close();

            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent it = new Intent(RouboActivity.this, dicasActivity.class);
        startActivity(it);
    }
}
