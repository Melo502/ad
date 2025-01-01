package com.shops.bean;

public class users {
    // 主键 ID
    private Integer id;

    // 用户手机号
    private String uphones;

    // 用户密码
    private String upwds;

    // 用户姓名
    private String unames;

    // 用户地址
    private String uaddrs;

    // 索引字段（分页用）
    private Integer sIndex;

    // 记录总数（分页用）
    private Integer sCount;

    // 是否登录标志
    private String isLogin;

    // 无参构造器
    public users() {
    }

    // 全参构造器
    public users(Integer id, String uphones, String upwds, String unames, String uaddrs, Integer sIndex, Integer sCount, String isLogin) {
        this.id = id;
        this.uphones = uphones;
        this.upwds = upwds;
        this.unames = unames;
        this.uaddrs = uaddrs;
        this.sIndex = sIndex;
        this.sCount = sCount;
        this.isLogin = isLogin;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUphones() {
        return uphones;
    }

    public void setUphones(String uphones) {
        this.uphones = uphones;
    }

    public String getUpwds() {
        return upwds;
    }

    public void setUpwds(String upwds) {
        this.upwds = upwds;
    }

    public String getUnames() {
        return unames;
    }

    public void setUnames(String unames) {
        this.unames = unames;
    }

    public String getUaddrs() {
        return uaddrs;
    }

    public void setUaddrs(String uaddrs) {
        this.uaddrs = uaddrs;
    }

    public Integer getsIndex() {
        return sIndex;
    }

    public void setsIndex(Integer sIndex) {
        this.sIndex = sIndex;
    }

    public Integer getsCount() {
        return sCount;
    }

    public void setsCount(Integer sCount) {
        this.sCount = sCount;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Tb_users{" +
                "id=" + id +
                ", uphones='" + uphones + '\'' +
                ", upwds='" + upwds + '\'' +
                ", unames='" + unames + '\'' +
                ", uaddrs='" + uaddrs + '\'' +
                ", sIndex=" + sIndex +
                ", sCount=" + sCount +
                ", isLogin='" + isLogin + '\'' +
                '}';
    }
}
