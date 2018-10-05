package com.example.julian.crudsqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ModifyActivity extends AppCompatActivity {

    private ArrayList<String> registros;
    private ArrayAdapter<String> adaptador;
    private ListView lv1;
    //
    private String nameDb = null;
    private SQLiteDatabase myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent intent = getIntent();
        nameDb = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
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
                final int posicion = i;
                //
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ModifyActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Elimina este teléfono ? " + posicion);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        TextView editTextId = findViewById(R.id.editTextId);
                        TextView editTextName = findViewById(R.id.editTextName);
                        finalMyCursor.moveToPosition(i);
                        editTextId.setText("" + finalMyCursor.getInt(0));
                        editTextName.setText("" + finalMyCursor.getString(1));
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                //
                return false;
            }
        });
        myCursor.close();
        finalMyCursor.close();
    }

    private void modifyBD() {
    }
}
