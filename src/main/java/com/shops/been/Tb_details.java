package com.shops.been;

public class Tb_details
{
    private Integer id;
    private String onos;
    private Integer tb_goods_id;


    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getOnos(){
        return onos;
    }
    public void setOnos(String onos){
        this.onos=onos;
    }

    public Integer getTb_goods_id(){
        return tb_goods_id;
    }
    public void setTb_goods_id(Integer tb_goods_id){
        this.tb_goods_id=tb_goods_id;
    }
    private String gnames;
    public String getGnames(){
        return gnames;
    }
    public void setGnames(String gnames){
        this.gnames=gnames;
    }
    private String gpics;
    public String getGpics(){
        return gpics;
    }
    public void setGpics(String gpics){
        this.gpics=gpics;
    }
    private Float gvals;
    public Float getGvals(){
        return gvals;
    }
    public void setGvals(Float gvals){
        this.gvals=gvals;
    }
    private Integer dnums;
    public Integer getDnums(){
        return dnums;
    }
    public void setDnums(Integer dnums){
        this.dnums=dnums;
    }
    private Integer Sindex;
    public Integer getSindex() {
        return Sindex;
    }
    public void setSindex(Integer sindex) {
        Sindex = sindex;
    }
    private Integer Scount;
    public Integer getScount() {
        return Scount;
    }
    public void setScount(Integer scount) {
        Scount = scount;
    }
}