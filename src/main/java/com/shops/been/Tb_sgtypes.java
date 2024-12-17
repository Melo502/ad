package com.shops.been;

public class Tb_sgtypes  //商品分类
{
    private Integer id;
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