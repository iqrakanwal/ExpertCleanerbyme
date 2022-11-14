package com.example.expertcleanerbyme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.expertcleanerbyme.R;

import java.io.IOException;
import java.io.InputStream;

public class CpuCooler extends AppCompatActivity {
    private LottieAnimationView animationView;
    final Handler handler = new Handler(Looper.getMainLooper());
    TextView textView ;
    ProcessBuilder processBuilder;
    String Holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process ;
    byte[] byteArry ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_cooler);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.playAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//Intent intent= new Intent(ScanActivity.this, AllScannedAudios.class);
//                intent.putExtra("mylist", audio);
//                    startActivity(intent);
                startActivity(new Intent(CpuCooler.this, DoneActivity.class));
                finish();

            }
        }, 11000);
        textView = (TextView)findViewById(R.id.textView);

        byteArry = new byte[1024];

        try{
            processBuilder = new ProcessBuilder(DATA);

            try {
                process = processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = process.getInputStream();

            while(inputStream.read(byteArry) != -1){

                Holder = Holder + new String(byteArry);
            }

            inputStream.close();

        } catch(IOException ex){

            ex.printStackTrace();
        }

        textView.setText(Holder);
    }
}