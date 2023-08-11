package com.krubo.apm;

import android.util.Log;

public class TimeCost {

    public void costTime() {
        long startTime = System.currentTimeMillis();
        long duration = System.currentTimeMillis() - startTime;
        Log.e("raotao", "Method: costTime; duration: " + duration);
    }
}
