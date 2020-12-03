package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;

public class MainActivity3 extends AppCompatActivity {

    BreakoutEngine be;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        LinearLayout surface = findViewById(R.id.surf);
        String difficulty = getIntent().getStringExtra("difficulty");
        boolean gyroscopeEnabled = getIntent().getBooleanExtra("gyroscopeEnabled", false);
        be = new BreakoutEngine(this, size.x, size.y, difficulty, gyroscopeEnabled);
        surface.addView(be);
    }

    protected void onResume() {
        super.onResume();
        be.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        be.pause();
    }
}