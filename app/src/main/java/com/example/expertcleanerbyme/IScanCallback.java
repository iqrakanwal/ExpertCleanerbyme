package com.example.expertcleanerbyme;


import com.example.model.JunkInfo;

import java.util.ArrayList;


public interface IScanCallback {
    void onBegin();

    void onProgress(JunkInfo info);

    void onFinish(ArrayList<JunkInfo> children);
}
