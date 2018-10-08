package com.example.julian.crudsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDB = null;
    boolean flagDB = false;
    public static final String EXTRA_MESSAGE = "com.example.julian.crudsqlite.MESSAGE";
    private static final String DATA_BASE_NAME = "myCrudSqlite.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = openOrCreateDatabase("myCrudSqlite.db", MODE_PRIVATE, null);
        if (myDB.isOpen()) {
            myDB.beginTransaction();
            Cursor myCursor = myDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' and name <> 'android_metadata'", null);
            myCursor.moveToFirst();
            if (myCursor.isFirst()) {
                flagDB = true;
            }
            if (flagDB == false) {
                myDB.execSQL("CREATE TABLE IF NOT EXISTS crud (id integer, name VARCHAR(200))");
                myCursor = myDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' and name <> 'android_metadata'", null);
                myCursor.moveToFirst();
                if (myCursor.isFirst()) {
                    flagDB = true;
                }
            }
            myDB.setTransactionSuccessful();
            myDB.endTransaction();
        }
        myDB.close();
    }

    public void insert(View view) {
        if (flagDB == true) {
            Intent intent = new Intent(this, InsertActivity.class);
            intent.putExtra(EXTRA_MESSAGE,DATA_BASE_NAME);
            startActivity(intent);
        }
    }

    public void modify(View view) {
        if (flagDB == true) {
            Intent intent = new Intent(this, ModifyActivity.class);
            intent.putExtra(EXTRA_MESSAGE,DATA_BASE_NAME);
            startActivity(intent);
        }

    }

    public void delete(View view) {
        if (flagDB == true) {
            Intent intent = new Intent(this, DeleteActivity.class);
            intent.putExtra(EXTRA_MESSAGE,DATA_BASE_NAME);
            startActivity(intent);
        }

    }

    public void list(View view) {
        if (flagDB == true) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        }

    }
}
