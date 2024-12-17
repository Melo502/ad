package com.example.advtotal1_0.pojo;

public class Ad {
    private int id; // 广告的ID
    private int advertiserId; // 广告主的ID
    private String title; // 广告标题
    private String description; // 广告描述
    private String imageUrl; // 广告图片URL
    private String url; // 广告链接
    private int typeId; // 广告的类型代号
    private String status; // 广告的状态（如：活跃、暂停等）
    private String typeKeyWords;//广告类型的关键字


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeKeyWords() {
        return typeKeyWords;
    }

    public void setTypeKeyWords(String typeKeyWords) {
        this.typeKeyWords = typeKeyWords;
    }
}

