package com.example.expertcleanerbyme.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.expertcleanerbyme.R;
import com.example.expertcleanerbyme.model.ApplicationkInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    Context context1;
    List<String> stringList;

    public AppsAdapter(Context context, List<String> list) {
        context1 = context;
        stringList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imageView;
        public CheckBox lock;
        public TextView textView_App_Name;

        //  public TextView textView_App_Package_Name;
        public ViewHolder(View view) {

            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            lock = (CheckBox) view.findViewById(R.id.lock);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            //   textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view2 = LayoutInflater.from(context1).inflate(R.layout.cardview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        ApplicationkInfo apkInfoExtractor = new ApplicationkInfo(context1);

        final String ApplicationPackageName = (String) stringList.get(position);
        final String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        viewHolder.textView_App_Name.setText(ApplicationLabelName);
viewHolder.lock.setChecked(true);
        // viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);

        viewHolder.imageView.setImageDrawable(drawable);


        viewHolder.lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package",ApplicationPackageName , null);
//                intent.setData(uri);
//                context1.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return stringList.size();
    }

    private void shareApplication(String packageName) throws PackageManager.NameNotFoundException {
        ApplicationInfo app = context1.getPackageManager().getApplicationInfo(packageName, 0);
        String name = context1.getPackageManager().getApplicationLabel(app).toString();

        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);


        intent.setType("*/*");

        File originalApk = new File(filePath);

        try {
            File tempFile = new File(context1.getExternalCacheDir() + "/ExtractedApk");
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            tempFile = new File(tempFile.getPath() + "/" + name + ".apk");
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            context1.startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shareFile(String packageName) {

        ApplicationInfo app = null;
        try {
            app = context1.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String name = context1.getPackageManager().getApplicationLabel(app).toString();

        // ApplicationInfo app = context1.getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(filePath);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        context1.startActivity(Intent.createChooser(sharingIntent, "Share Apk using"));

    }


    public void shareFiles(String packageName) {
//
//            File file ; // your code
//            ApplicationInfo app = null;
//            try {
//                app = context1.getPackageManager().getApplicationInfo(packageName, 0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            String name =context1.getPackageManager().getApplicationLabel(app).toString();
//
//            // ApplicationInfo app = context1.getApplicationContext().getApplicationInfo();
//            String filePath = app.sourceDir;
//            Intent install = new Intent(Intent.ACTION_VIEW);
//            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            // Old Approach
//            install.setDataAndType(Uri.fromFile(new File(filePath)), "abc");
//            // End Old approach
//            // New Approach
//            Uri apkURI = FileProvider.getUriForFile(
//                    context1,
//                    context1.getApplicationContext()
//                            .getPackageName() + ".provider", new File(filePath));
//            install.setDataAndType(apkURI, "abc");
//            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            // End New Approach
//            context1.startActivity(install);

//            ApplicationInfo app = context1.getApplicationContext().getApplicationInfo();
//            String filePath = app.sourceDir;
//
//            Intent intent = new Intent(Intent.ACTION_SEND);
//
//            // MIME of .apk is "application/vnd.android.package-archive".
//            // but Bluetooth does not accept this. Let's use "*/*" instead.
//            intent.setType("*/*");
//
//
//            // Append file and send Intent
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//            context1.startActivity(Intent.createChooser(intent, "Share app via"));
//            ApplicationInfo app = context1.getApplicationContext().getApplicationInfo();
//            String filePath = app.sourceDir;
////
//            (context1, "hello"+filePath, Toast.LENGTH_SHORT).show();
//            Intent shareApkIntent = new Intent();
//            shareApkIntent.setAction(Intent.ACTION_SEND);
//            shareApkIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//            shareApkIntent.setType("application/vnd.android.package-archive");
//
//            context1.startActivity(Intent.createChooser(shareApkIntent, context1.getString(R.string.share_apk_dialog_title)));


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("package:" + packageName));
        context1.startActivity(intent);

    }


}