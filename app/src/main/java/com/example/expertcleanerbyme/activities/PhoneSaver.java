package com.example.expertcleanerbyme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.expertcleanerbyme.R;

public class PhoneSaver extends AppCompatActivity {
    private LottieAnimationView animationView;
    final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_saver);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.playAnimation();
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

//        wifiManager.getDhcpInfo();
       // Toast.makeText(this, ""+wifiManager.getDhcpInfo(), Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//Intent intent= new Intent(ScanActivity.this, AllScannedAudios.class);
//                intent.putExtra("mylist", audio);
//                    startActivity(intent);
                startActivity(new Intent(PhoneSaver.this, DoneActivity.class));
                finish();

            }
        }, 5000);

    }
}