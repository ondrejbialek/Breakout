package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button button, button2;
    Switch sw;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String difficulty = "Easy";
    boolean gyroscopeEn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup1);

        sw = findViewById(R.id.switch1);
        sw.setText("OFF");
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sw.isChecked()){
                    gyroscopeEn = true;
                    sw.setText("ON");
                }
                else {
                    gyroscopeEn = false;
                    sw.setText("OFF");
                }
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity2();
            }
        });
    }

    public void openNewActivity(){
        Intent i = new Intent(this, MainActivity3.class);
        i.putExtra("difficulty", difficulty);
        i.putExtra("gyroscopeEnabled", gyroscopeEn);
        startActivity(i);
    }

    public void openNewActivity2(){
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }

    public void radioButtonClick(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        difficulty = radioButton.getText().toString();
        System.out.println(difficulty);
    }
}