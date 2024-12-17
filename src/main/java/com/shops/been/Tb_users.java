package com.shops.been;

public class Tb_users
{
    private Integer id;
    private String uphones;
    private String upwds;
    private String unames;
    private String uaddrs;
    private Integer Sindex;
    private Integer Scount;
    private String isLogin;



    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getUphones(){
        return uphones;
    }
    public void setUphones(String uphones){
        this.uphones=uphones;
    }


    public String getUpwds(){
        return upwds;
    }
    public void setUpwds(String upwds){
        this.upwds=upwds;
    }

    public String getUnames(){
        return unames;
    }
    public void setUnames(String unames){
        this.unames=unames;
    }

    public String getUaddrs(){
        return uaddrs;
    }
    public void setUaddrs(String uaddrs){
        this.uaddrs=uaddrs;
    }

    public Integer getSindex() {
        return Sindex;
    }
    public void setSindex(Integer sindex) {
        Sindex = sindex;
    }

    public Integer getScount() {
        return Scount;
    }
    public void setScount(Integer scount) {
        Scount = scount;
    }

    public String getIsLogin() {
        return isLogin;
    }
    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

}