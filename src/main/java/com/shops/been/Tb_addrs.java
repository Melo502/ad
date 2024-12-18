package com.shops.been;

public class Tb_addrs
{
    private Integer id;
    private Integer tb_users_id;
    private String tbaphones;
    private String tbanames;
    private String tbaaddrs;
    private String uphones;
    private String upwds;
    private String unames;
    private String uaddrs;
    private Integer Sindex;
    private Integer Scount;


    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public Integer getTb_users_id(){
        return tb_users_id;
    }
    public void setTb_users_id(Integer tb_users_id){
        this.tb_users_id=tb_users_id;
    }

    public String getTbaphones(){
        return tbaphones;
    }
    public void setTbaphones(String tbaphones){
        this.tbaphones=tbaphones;
    }

    public String getTbanames(){
        return tbanames;
    }
    public void setTbanames(String tbanames){
        this.tbanames=tbanames;
    }

    public String getTbaaddrs(){
        return tbaaddrs;
    }
    public void setTbaaddrs(String tbaaddrs){
        this.tbaaddrs=tbaaddrs;
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
}