package com.example.advtotal1_0.pojo;

// 广告投放记录类，记录广告的展示和点击信息
public class AdRecord {
    private int id; // 广告投放记录ID
    private int adId; // 广告ID，指向广告表

    private int clickCount;// 广告的点击次数
    private double income;// 广告的收益

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


    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
