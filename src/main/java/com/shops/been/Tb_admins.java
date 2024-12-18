package com.shops.been;

public class Tb_admins
{
    private Integer id;
    private String anos;
    private String apwds;
    private String anames;
    private String aphones;
    private Integer Sindex;
    private Integer Scount;


    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getAnos(){
        return anos;
    }
    public void setAnos(String anos){
        this.anos=anos;
    }

    public String getApwds(){
        return apwds;
    }
    public void setApwds(String apwds){
        this.apwds=apwds;
    }

    public String getAnames(){
        return anames;
    }
    public void setAnames(String anames){
        this.anames=anames;
    }

    public String getAphones(){
        return aphones;
    }
    public void setAphones(String aphones){
        this.aphones=aphones;
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