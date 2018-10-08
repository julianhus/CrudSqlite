package com.example.julian.crudsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private String nameDb = null;
    private SQLiteDatabase myDB = null;
    //
    private ArrayList<String> registros;
    private ArrayAdapter<String> adaptador;
    private ListView lv1;
    int posicion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Intent intent = getIntent();
        nameDb = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        myDB = openOrCreateDatabase(nameDb, MODE_PRIVATE, null);
        myDB = openOrCreateDatabase(nameDb, MODE_PRIVATE, null);
        registros = new ArrayList<String>();
        Cursor myCursor = null;
        if (myDB.isOpen()) {
            myCursor = myDB.rawQuery("select * from crud", null);
            while (myCursor.moveToNext()) {
                int id = myCursor.getInt(0);
                String name = myCursor.getString(1);
                registros.add(id + " - " + name);
            }
        }
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, registros);
        lv1 = (ListView) findViewById(R.id.listView);
        lv1.setAdapter(adaptador);
        final Cursor finalMyCursor = myCursor;
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                posicion = i;
                TextView editTextId = editTextId = findViewById(R.id.editTextId);
                TextView editTextName = editTextName = findViewById(R.id.editTextName);
                finalMyCursor.moveToPosition(i);
                editTextId.setText("" + finalMyCursor.getInt(0));
                editTextName.setText("" + finalMyCursor.getString(1));
                return false;
            }
        });
    }

    public void deleteDB (View view){
        TextView editTextId = editTextId = findViewById(R.id.editTextId);
        TextView editTextName = editTextName = findViewById(R.id.editTextName);
        if (myDB.isOpen()) {
            myDB.execSQL("delete from crud where rowid = " + (posicion + 1));
        }
        startActivity(this.getIntent());
    }
}
