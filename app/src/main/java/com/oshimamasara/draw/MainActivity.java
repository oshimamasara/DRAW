package com.oshimamasara.draw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = (PaintView) findViewById(R.id.canvas);
    }

    public void clearCanvas(View v) {
        paintView.clearCanvas();

    }
}
