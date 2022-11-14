package com.example.expertcleanerbyme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.expertcleanerbyme.MainActivity;
import com.example.expertcleanerbyme.R;
import com.example.expertcleanerbyme.utils.DiskStat;
import com.example.expertcleanerbyme.utils.MemStat;

public class Splashscreen extends AppCompatActivity {
    public static final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedPreferences sharedPref =getSharedPreferences("preference", Context.MODE_PRIVATE);
        final int highScore = sharedPref.getInt(getString(R.string.started), 0);
//        final Handler handler = new Handler();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final DiskStat diskStat = new DiskStat();
//                final MemStat memStat = new MemStat(Splashscreen.this);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
//                        intent.putExtra(MainActivity.PARAM_TOTAL_SPACE, diskStat.getTotalSpace());
//                        intent.putExtra(MainActivity.PARAM_USED_SPACE, diskStat.getUsedSpace());
//                        intent.putExtra(MainActivity.PARAM_TOTAL_MEMORY, memStat.getTotalMemory());
//                        intent.putExtra(MainActivity.PARAM_USED_MEMORY, memStat.getUsedMemory());
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//            }
//        });
//
//        thread.start();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(highScore==0){
                        Intent mainIntent = new Intent(Splashscreen.this, Getstarted.class);
                        startActivity(mainIntent);
                        finish();
                }

                else if(highScore==1){
                        Intent mainIntent = new Intent(Splashscreen.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


    }
    }
