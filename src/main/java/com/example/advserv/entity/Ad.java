package com.example.advserv.entity;


import java.time.LocalDateTime;

public class Ad {

    private int id;                  // 广告ID（数据库自增）
    private String title;            // 广告标题
    private String description;      // 广告描述
    private String adImageUrl;       // 广告图片的URL（或者视频URL）
    private String adLink;           // 广告链接（点击广告时跳转的页面）
    private String adCategory;       // 广告类别（如：电子产品、时尚、教育等）
    private String keywords;         // 关键字，用于广告的关键词匹配
    private LocalDateTime startTime; // 广告投放开始时间
    private LocalDateTime endTime;   // 广告投放结束时间
    private String targetLocation;   // 广告投放位置（如：网站顶部、首页横幅等）
    private double budget;           // 广告预算（投放该广告的预算）
    private double costPerClick;     // 每次点击的费用（CPC）
    private double costPerImpression; // 每千次展示的费用（CPM）
    private int clickCount;          // 广告点击次数（动态更新）
    private int impressionCount;     // 广告展示次数（动态更新）
    private double totalSpent;       // 广告花费（动态更新）
    private String status;           // 广告状态 (正在投放、暂停、已结束)  [ACTIVE、PAUSED、ENDED]
    private String admaster;         // 广告主用户名
    // 构造函数
    public Ad(int id, String title, String description, String adImageUrl, String adLink,
              String adCategory, String keywords, LocalDateTime startTime, LocalDateTime endTime,
              String targetLocation, double budget, double costPerClick, double costPerImpression,String status,String admaster) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.adImageUrl = adImageUrl;
        this.adLink = adLink;
        this.adCategory = adCategory;
        this.keywords = keywords;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.targetLocation = targetLocation;
        this.budget = budget;
        this.costPerClick = costPerClick;
        this.costPerImpression = costPerImpression;
        this.clickCount = 0;          // 默认初始点击次数为0
        this.impressionCount = 0;     // 默认初始展示次数为0
        this.totalSpent = 0.0;        // 默认初始广告花费为0
        this.admaster = admaster;
    }

    public Ad(){  //无参构造方法
    }

    // Getter 和 Setter 方法

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(String adImageUrl) {
        this.adImageUrl = adImageUrl;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(String adCategory) {
        this.adCategory = adCategory;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getCostPerClick() {
        return costPerClick;
    }

    public void setCostPerClick(double costPerClick) {
        this.costPerClick = costPerClick;
    }

    public double getCostPerImpression() {
        return costPerImpression;
    }

    public void setCostPerImpression(double costPerImpression) {
        this.costPerImpression = costPerImpression;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getImpressionCount() {
        return impressionCount;
    }

    public void setImpressionCount(int impressionCount) {
        this.impressionCount = impressionCount;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getAdmaster() {
        return admaster;
    }

    public void setAdmaster(String admaster) {
        this.admaster = admaster;
    }
}

