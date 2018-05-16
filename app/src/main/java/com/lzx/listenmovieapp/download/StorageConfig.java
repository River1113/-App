package com.lzx.listenmovieapp.download;

import android.os.Environment;

import java.io.File;

/**
 * Author：Road
 * Date  ：2018/5/9
 */

public class StorageConfig {

    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory() + File.separator + "ListenMovie" + File.separator + "video";
    public static final String AUDIO_PATH = Environment.getExternalStorageDirectory() + File.separator + "ListenMovie" + File.separator + "wav";

    public static File getVideoPath() {
        File file = new File(VIDEO_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getAudioPath() {
        File file = new File(AUDIO_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
