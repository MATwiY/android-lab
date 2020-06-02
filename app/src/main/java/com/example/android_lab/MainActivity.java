package com.example.android_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> target;
    private SimpleCursorAdapter adapter;
    MySQLite db = new MySQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] values = new String[] {
                "Pies",
                "Kot",
                "Dzik",
                "Słoń",
                "Mysz",
                "Borsuk",
                "Krokodyl",
                "Koń"
        };
        this.target = new ArrayList<String>();
        this.target.addAll(Arrays.asList(values));

        this.adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                db.lista(),
                new String[] { "_id", "gatunek"},
                new int[] {android.R.id.text1, android.R.id.text2},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView)view.findViewById(android.R.id.text1);

                Animal zwierz = db.pobierz(Integer.parseInt(name.getText().toString()));
                Log.e("E", zwierz.getGatunek());
                Intent intent = new Intent(getApplicationContext(), DodajWpis.class);
                intent.putExtra("element", zwierz);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void nowyWpis(MenuItem menuItem){
        Intent intent = new Intent(this, DodajWpis.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Animal nowy = (Animal)extras.getSerializable("nowy");

            this.db.dodaj(nowy);
            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();

            /*String nowy = (String) extras.get("wpis");
            target.add(nowy);
            adapter.notifyDataSetChanged();*/
        }
        if(requestCode == 2 && resultCode == RESULT_OK){

            Bundle extras = data.getExtras();
            assert extras != null;
            Animal edit = (Animal)extras.getSerializable("nowy");
            this.db.aktualizuj(edit);
            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();
        }
    }
}
