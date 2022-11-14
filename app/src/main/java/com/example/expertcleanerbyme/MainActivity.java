package com.example.expertcleanerbyme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.expertcleanerbyme.activities.AppManager;
import com.example.expertcleanerbyme.activities.CpuCooler;
import com.example.expertcleanerbyme.activities.Junkcleaner;
import com.example.expertcleanerbyme.activities.PhoneSaver;
import com.example.expertcleanerbyme.activities.SettingsActivity;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout junkcleaner, appmanager, cpucooler, phonesaver;
    private DrawerLayout mNavDrawer;
    NavigationView navigationView;
    ArcProgress arcProgress1;
    public static final String PARAM_TOTAL_SPACE = "total_space";
    public static final String PARAM_USED_SPACE = "used_space";
    public static final String PARAM_TOTAL_MEMORY = "total_memory";
    public static final String PARAM_USED_MEMORY = "used_memory";
    private Toolbar toolbar;
    long usedMemory;
    private Handler mHandler = new Handler();

    ImageView setting, drawer;
    //VideoView videoView;
   // CircularProgressIndicator circularProgress;
    private final Runnable mRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void run() {
            //scan();
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            long freeMemory = mi.availMem;
            String freememoryString = getFileSize(freeMemory);
            long totalMemory = mi.totalMem;
            String usedMemoryString = getFileSize(usedMemory);
            String totalMemoryString = getFileSize(totalMemory);
            usedMemory = totalMemory - freeMemory;
            long totalsizeInMb = totalMemory / (1024 * 1024);
            arcProgress1.setMax(100);
            long sizeInMb = usedMemory / (1024 * 1024);
            long percentage =sizeInMb*100/totalsizeInMb;
            double res = (usedMemory / 100.0f) * totalMemory;
            arcProgress1.setProgress((int) (percentage));
        //    ram.setText(usedMemoryString+" used out of "+totalMemoryString+"");
            double totalSize = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
            double totMb = totalSize / (1024 * 1024);
            double availableSize = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
            double freeMb = availableSize / (1024 * 1024);
            double usedexternal= totMb-freeMb;
            mHandler.postDelayed(mRunnable, 1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drwaer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intView();
        Toast.makeText(this, "" + getMemorySizeHumanized(), Toast.LENGTH_SHORT).show();
        mNavDrawer = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mNavDrawer,
//                toolbar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
        //  mNavDrawer.addDrawerListener(actionBarDrawerToggle);
        //   actionBarDrawerToggle.syncState();

        try {

            String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder processBuilder = new ProcessBuilder(DATA);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            byte[] byteArry = new byte[1024];
            String output = "";
            while (inputStream.read(byteArry) != -1) {
                output = output + new String(byteArry);
            }
            inputStream.close();

            Log.d("CPU_INFO", output);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }
        else {
            bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        }

        mHandler.postDelayed(mRunnable, 1000);
       long megAvailable = bytesAvailable / (1024 * 1024);
        Log.e("","Available MB : "+megAvailable);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        ActivityManager actvityManager = (ActivityManager)
                this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
        Toast.makeText(this, "" + procInfos.size(), Toast.LENGTH_SHORT).show();

        File path = Environment.getDataDirectory();
        StatFs stat2 = new StatFs(path.getPath());
        long blockSize = stat2.getBlockSize();
        long availableBlocks = stat2.getAvailableBlocks();
        String format = Formatter.formatFileSize(this, availableBlocks * blockSize);
        Log.e("", "Format : " + format);
        junkcleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Junkcleaner.class));

            }
        });

        appmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AppManager.class));

            }
        });

        cpucooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CpuCooler.class));

            }
        });

        phonesaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PhoneSaver.class));

            }
        });
        drawer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (!mNavDrawer.isDrawerOpen(Gravity.START)) mNavDrawer.openDrawer(Gravity.START);
                else mNavDrawer.closeDrawer(Gravity.END);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    //    circularProgress = findViewById(R.id.circular_progress);
// you can set max and current progress values individually
     //   circularProgress.setMaxProgress(100);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long freeMemory = mi.availMem;
        long totalMemory = mi.totalMem;
        usedMemory = totalMemory - freeMemory;
        String usedMemoryString = getFileSize(usedMemory);
        long totalsizeInMb = totalMemory / (1024 * 1024);
        long sizeInMb = usedMemory / (1024 * 1024);

        long percentage =sizeInMb*100/totalsizeInMb;

     //   circularProgress.setProgress((int) percentage, 100);
       // circularProgress.setBottomText("Ram Used");



// or all at once
        String path1 = "android.resource://" + getPackageName() + "/" + R.raw.com;
        //videoView.setVideoURI(Uri.parse(path));
        //videoView.start();    }
        mNavDrawer.closeDrawer(GravityCompat.START);


    }

    private void intView() {
        junkcleaner = findViewById(R.id.junkcleaner);
        appmanager = findViewById(R.id.appmananger);
        cpucooler = findViewById(R.id.cpucooler);
        phonesaver = findViewById(R.id.phonesaver);
        arcProgress1 =  findViewById(R.id.arc_progress1);
        setting = findViewById(R.id.settings);
        drawer = findViewById(R.id.drw);
        //videoView=findViewById(R.id.video);
        //circularProgress = findViewById(R.id.circular_progress);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.removeads:
                break;
            case R.id.rateus:
                break;
            case R.id.share:

                break;
            case R.id.Feedback:

                break;
            case R.id.moreapp:

                break;
            case R.id.privacy:

                break;

        }

        mNavDrawer.closeDrawer(GravityCompat.START);


        return true;

    }
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    public String getMemorySizeHumanized() {
        Context context = getApplicationContext();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(memoryInfo);

        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");

        String finalValue = "";
        long totalMemory = memoryInfo.totalMem;

        double kb = totalMemory / 1024.0;
        double mb = totalMemory / 1048576.0;
        double gb = totalMemory / 1073741824.0;
        double tb = totalMemory / 1099511627776.0;

        if (tb > 1) {
            finalValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            finalValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" MB");
        } else if (kb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" KB");
        } else {
            finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
        }

        return finalValue;
    }
}