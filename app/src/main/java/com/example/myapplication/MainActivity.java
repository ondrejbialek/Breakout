package com.example.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {

    BreakoutEngine breakoutEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        breakoutEngine = new BreakoutEngine(this, size.x, size.y);
        setContentView(breakoutEngine);
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