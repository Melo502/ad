package com.shops.been;

public class Tb_goods
{
    private Integer id;
    private String gnames;
    private String gpics;
    private Float gvals;
    private Integer tb_sgtypes_id;
    private String gmarks;
    private String gflags;
    private String sgtnames;
    private Integer tb_fgtypes_id;
    private String fgtname;
    private Integer Sindex;
    private Integer Scount;


    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getGnames(){
        return gnames;
    }
    public void setGnames(String gnames){
        this.gnames=gnames;
    }

    public String getGpics(){
        return gpics;
    }
    public void setGpics(String gpics){
        this.gpics=gpics;
    }

    public Float getGvals(){
        return gvals;
    }
    public void setGvals(Float gvals){
        this.gvals=gvals;
    }

    public Integer getTb_sgtypes_id(){
        return tb_sgtypes_id;
    }
    public void setTb_sgtypes_id(Integer tb_sgtypes_id){
        this.tb_sgtypes_id=tb_sgtypes_id;
    }

    public String getGmarks(){
        return gmarks;
    }
    public void setGmarks(String gmarks){
        this.gmarks=gmarks;
    }

    public String getGflags(){
        return gflags;
    }
    public void setGflags(String gflags){
        this.gflags=gflags;
    }

    public String getSgtnames(){
        return sgtnames;
    }
    public void setSgtnames(String sgtnames){
        this.sgtnames=sgtnames;
    }

    public Integer getTb_fgtypes_id(){
        return tb_fgtypes_id;
    }
    public void setTb_fgtypes_id(Integer tb_fgtypes_id){
        this.tb_fgtypes_id=tb_fgtypes_id;
    }

    public String getFgtname(){
        return fgtname;
    }
    public void setFgtname(String fgtname){
        this.fgtname=fgtname;
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