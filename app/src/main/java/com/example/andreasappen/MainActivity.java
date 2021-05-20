package com.example.andreasappen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.about);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OmActivity.class);
            startActivity(intent);
        });

    }
}