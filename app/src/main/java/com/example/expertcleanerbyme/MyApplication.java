package com.example.expertcleanerbyme;
import android.app.Application;
public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;







    }

}
