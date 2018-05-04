package com.lzx.listenmovieapp.bean;

import java.io.Serializable;

/**
 * Author：Road
 * Date  ：2018/5/4
 */

public class EventBean implements Serializable {
    //  活动Id
    private int eventId;

    //  活动名称
    private String eventName;

    //   活动封面
    private String coverImage;

    //   活动状态(-1.default, 1.进行中, 2.未开始，3.已结束)
    private int status;

    //    活动开始时间
    private String startTime;

    //    活动结束时间
    private String endTime;

    //    活动地址
    private String address;

    // 活动描述
    private String description;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
