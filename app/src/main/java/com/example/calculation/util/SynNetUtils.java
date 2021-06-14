package com.example.calculation.util;

import android.os.Handler;
import android.util.Log;

public class SynNetUtils {
    public static void get(final String url, final Callback callback) {
        final Handler handler = new Handler();
        new Thread(() -> {
            final String response = NetUtils.get(url);
            handler.post(() -> callback.onResponse(response));
        }).start();
        Log.v("1414",url);
    }

    public static void post(final String url, final String content, final Callback callback) {
        final Handler handler = new Handler();
        new Thread(() -> {
            final String response = NetUtils.post(url, content);
            handler.post(() -> callback.onResponse(response));
        }).start();
        Log.v("1414",url);
        Log.v("1414",content);
    }

    public interface Callback {
        void onResponse(String response);
    }
}
