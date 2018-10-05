package com.example.julian.crudsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InsertActivity extends AppCompatActivity {

    private String nameDb = null;
    private SQLiteDatabase myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Intent intent = getIntent();
        nameDb = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    }

    public void insert(View view) {
        final Intent intent = new Intent(this, MainActivity.class);
        TextView editTextId = findViewById(R.id.editTextId);
        TextView editTextNombre = findViewById(R.id.editTextNombre);
        myDB = openOrCreateDatabase(nameDb, MODE_PRIVATE, null);
        if (myDB.isOpen()) {
            ContentValues row = new ContentValues();
            row.put("id", editTextId.getText().toString());
            row.put("name", editTextNombre.getText().toString());
            myDB.beginTransaction();
            myDB.insert("crud", null, row);
            myDB.setTransactionSuccessful();
            myDB.endTransaction();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Confir");
            dialog.setMessage("record created");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Confir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(intent);
                }
            });
            dialog.show();
            editTextId.setText("");
            editTextNombre.setText("");
        }
        myDB.close();
    }
}
