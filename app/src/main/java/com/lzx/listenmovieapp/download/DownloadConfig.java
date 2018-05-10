package com.lzx.listenmovieapp.download;

import android.os.Environment;

import java.io.File;

/**
 * Author：Road
 * Date  ：2018/5/9
 */

public class DownloadConfig {

    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory() + File.separator + "ListenMovie" + File.separator + "video";

    public static File getFilePath() {
        File file = new File(VIDEO_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
