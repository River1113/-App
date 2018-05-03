package com.lzx.listenmovieapp.utils;

/**
 * Author：Road
 * Date  ：2018/3/1
 */

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by aa on 2016/3/7.
 */
public class ToastUtil {

    public static void show(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mToast != null) {
                mToast.cancel();
            }
        }
    };

    public synchronized static void show(Context context, String text, int duration) {
        mHandler.removeCallbacks(runnable);
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
        mHandler.postDelayed(runnable, 1500);
    }
}
