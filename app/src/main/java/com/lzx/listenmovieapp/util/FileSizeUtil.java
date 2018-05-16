package com.lzx.listenmovieapp.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/6/12.
 */

public class FileSizeUtil {

    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
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
