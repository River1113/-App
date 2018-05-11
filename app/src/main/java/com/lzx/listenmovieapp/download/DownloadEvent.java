package com.lzx.listenmovieapp.download;

import com.lzx.listenmovieapp.bean.MovieListInfo;

/**
 * Author：Road
 * Date  ：2018/5/10
 */

public class DownloadEvent {
    private MovieListInfo info;

    public DownloadEvent(MovieListInfo info) {
        this.info = info;
    }

    public MovieListInfo getInfo() {
        return info;
    }

    public void setInfo(MovieListInfo info) {
        this.info = info;
    }
}
