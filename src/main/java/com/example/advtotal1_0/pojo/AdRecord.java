package com.example.advtotal1_0.pojo;

import java.util.Date;

// 广告投放记录类，记录广告的展示和点击信息
public class AdRecord {
    private int id; // 广告投放记录ID
    private int adId; // 广告ID，指向广告表
    private double income;// 广告的收益
    private Date clickTime;//广告的点击时间
    private String website;//投放的网站

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
