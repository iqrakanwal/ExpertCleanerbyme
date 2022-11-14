package com.example.expertcleanerbyme.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.expertcleanerbyme.R;
import com.example.expertcleanerbyme.adaptor.AppsAdapter;
import com.example.expertcleanerbyme.model.ApplicationkInfo;

public class AppManager extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        recyclerView =  findViewById(R.id.recyclarview);
        recyclerViewLayoutManager = new LinearLayoutManager(AppManager.this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        adapter = new AppsAdapter(AppManager.this, new ApplicationkInfo(AppManager.this).GetAllInstalledApkInfo());
        recyclerView.setAdapter(adapter);
    }

}