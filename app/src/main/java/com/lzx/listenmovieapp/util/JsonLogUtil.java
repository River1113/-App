package com.lzx.listenmovieapp.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author：Road
 * Date  ：2017/11/1
 */

public class JsonLogUtil {
    private static final String TAG = "===";
    private static final String HEAD = "---";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void log(String msg) {
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(TAG, true);
        message = HEAD + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(TAG, "║ " + line);
        }
        printLine(TAG, false);
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════╗");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }
}
