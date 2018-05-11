package com.lzx.listenmovieapp.bean;

import java.io.Serializable;

/**
 * Author：Road
 * Date  ：2018/4/27
 */

public class MovieListInfo implements Serializable {
    private String img;
    private String name;
    private String desc;
    private String score;
    private int movieType;
    private String url;
    private long size;
    private String movieName;


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public int getMovieType() {
        return movieType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
