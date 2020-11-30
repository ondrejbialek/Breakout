package com.example.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends Activity {

    BreakoutEngine breakoutEngine;
    Button button;
    Switch sw;
    public static boolean gyroscopeOnOff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        breakoutEngine = new BreakoutEngine(this, size.x, size.y);
        setContentView(R.layout.activity_main);

        sw = findViewById(R.id.switch1);
        sw.setText("OFF");
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sw.isChecked()){
                    gyroscopeOnOff = true;
                    sw.setText("ON");
                }
                else {
                    gyroscopeOnOff = false;
                    sw.setText("OFF");
                }
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(breakoutEngine);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        breakoutEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        breakoutEngine.pause();
    }
}