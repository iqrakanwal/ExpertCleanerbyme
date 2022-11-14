package com.example.expertcleanerbyme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.expertcleanerbyme.MainActivity;
import com.example.expertcleanerbyme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Getstarted extends AppCompatActivity {
    FloatingActionButton button;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        checkBox = findViewById(R.id.checkBox);
        SharedPreferences sharedPref = getSharedPreferences("preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.started), 1);
        editor.apply();
        editor.commit();
        button = findViewById(R.id.floatingActionButton2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    startActivity(new Intent(Getstarted.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Getstarted.this, "please check first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
