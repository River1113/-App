package com.lzx.listenmovieapp.bean;

import java.io.Serializable;

/**
 * Author：Road
 * Date  ：2018/4/27
 */

public class MovieListInfo implements Serializable{
    private String img;
    private String name;
    private String desc;
    private String score;
    

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
