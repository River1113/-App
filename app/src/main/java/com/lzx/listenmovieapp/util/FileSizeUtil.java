package com.lzx.listenmovieapp.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/6/12.
 */

public class FileSizeUtil {

    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    public static String getFileFormatSize(File file) throws Exception {
        String result = null;
        int sub_string = 0;
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        // 如果文件长度大于1GB
        if (size >= 1073741824) {
            sub_string = String.valueOf((float) size / 1073741824).indexOf(
                    ".");
            result = ((float) size / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (size >= 1048576) {
            // 如果文件长度大于1MB且小于1GB,substring(int beginIndex, int endIndex)
            sub_string = String.valueOf((float) size / 1048576).indexOf(".");
            result = ((float) size / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (size >= 1024) {
            // 如果文件长度大于1KB且小于1MB
            sub_string = String.valueOf((float) size / 1024).indexOf(".");
            result = ((float) size / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (size < 1024)
            result = Long.toString(size) + "B";
        return result;
    }

    public static String getFileFormatSize(int size) {
        String result = null;
        int sub_string = 0;
        // 如果文件长度大于1GB
        if (size >= 1073741824) {
            sub_string = String.valueOf((float) size / 1073741824).indexOf(
                    ".");
            result = ((float) size / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (size >= 1048576) {
            // 如果文件长度大于1MB且小于1GB,substring(int beginIndex, int endIndex)
            sub_string = String.valueOf((float) size / 1048576).indexOf(".");
            result = ((float) size / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (size >= 1024) {
            // 如果文件长度大于1KB且小于1MB
            sub_string = String.valueOf((float) size / 1024).indexOf(".");
            result = ((float) size / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (size < 1024)
            result = Long.toString(size) + "B";
        return result;
    }

    public static String getFileFormatSize(long size) {
        String result = null;
        int sub_string = 0;
        // 如果文件长度大于1GB
        if (size >= 1073741824) {
            sub_string = String.valueOf((float) size / 1073741824).indexOf(
                    ".");
            result = ((float) size / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (size >= 1048576) {
            // 如果文件长度大于1MB且小于1GB,substring(int beginIndex, int endIndex)
            sub_string = String.valueOf((float) size / 1048576).indexOf(".");
            result = ((float) size / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (size >= 1024) {
            // 如果文件长度大于1KB且小于1MB
            sub_string = String.valueOf((float) size / 1024).indexOf(".");
            result = ((float) size / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (size < 1024)
            result = Long.toString(size) + "B";
        return result;
    }
}
