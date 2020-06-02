package com.example.android_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DodajWpis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wpis);
    }

    public void wyslij(View view){
        EditText kontrolka = (EditText) findViewById(R.id.editText);
        String pole = kontrolka.getText().toString();

        Intent intecja = new Intent();
        intecja.putExtra("wpis", pole);
        setResult(RESULT_OK, intecja);
        finish();
    }
}
