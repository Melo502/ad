package com.example.advtotal1_0.pojo;

import java.util.Date;

public class AdPlacement {
    private int id;
    private int adId;
    private String website;
    private int userId;
    private Date placedAt;

    // 无参构造方法
    public AdPlacement() {}

    // 有参构造方法
    public AdPlacement(int adId, String website, int userId, Date placedAt) {
        this.adId = adId;
        this.website = website;
        this.userId = userId;
        this.placedAt = placedAt;
    }

    // Getters 和 Setters
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    @Override
    public String toString() {
        return "AdPlacement{" +
                "id=" + id +
                ", adId=" + adId +
                ", website='" + website + '\'' +
                ", userId='" + userId + '\'' +
                ", placedAt=" + placedAt +
                '}';
    }
}
