package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ListView listView1, listView2, listView3;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dh = new DatabaseHelper(this);

        listView1 = findViewById(R.id.listView1);
        listView2 = findViewById(R.id.listView2);
        listView3 = findViewById(R.id.listView3);

        InsertToListView("Easy", listView1);
        InsertToListView("Normal", listView2);
        InsertToListView("Hard", listView3);
    }

    public void InsertToListView(String difficulty, ListView listView){
        Cursor cursor = dh.select(difficulty);
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            if (cursor.moveToNext()) {
                list.add((i + 1) + ". Score: " + cursor.getString(1) + "  (" + cursor.getString(3) + ")");
                ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(listAdapter);
            } else {
                list.add((i + 1) + ". Score: -");
                ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(listAdapter);
            }
        }
    }
}