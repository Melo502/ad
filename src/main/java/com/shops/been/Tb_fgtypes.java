package com.shops.been;

import java.util.List;

public class Tb_fgtypes    //商品主类
{
    private Integer id;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    private String fgtname;
    public String getFgtname(){
        return fgtname;
    }
    public void setFgtname(String fgtname){
        this.fgtname=fgtname;
    }
    private List<Tb_sgtypes> sgtlist;
    public List<Tb_sgtypes> getSgtlist() {
        return sgtlist;
    }
    public void setSgtlist(List<Tb_sgtypes> sgtlist) {
        this.sgtlist = sgtlist;
    }
}