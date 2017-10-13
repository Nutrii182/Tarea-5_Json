package com.example.nart1.tarea_5_json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseApiFragment baseApiAdaper = new BaseApiFragment ();

        getSupportFragmentManager().beginTransaction ()
                .replace (R.id.rootContainer, baseApiAdaper)
                .commit ();
    }
}
