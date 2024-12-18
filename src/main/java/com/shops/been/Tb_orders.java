package com.shops.been;

import java.util.List;

public class Tb_orders
{
    private Integer id;
    private String onos;
    private Integer tb_users_id;
    private String tbanames;
    private String tbaphones;
    private String tbaaddrs;
    private String otimes;
    private String oflags;
    private List<Tb_details> tbdlist;
    private Integer Sindex;
    private Integer Scount;



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

    public Integer getTb_users_id(){
        return tb_users_id;
    }
    public void setTb_users_id(Integer tb_users_id){
        this.tb_users_id=tb_users_id;
    }

    public String getTbanames(){
        return tbanames;
    }
    public void setTbanames(String tbanames){
        this.tbanames=tbanames;
    }

    public String getTbaphones(){
        return tbaphones;
    }
    public void setTbaphones(String tbaphones){
        this.tbaphones=tbaphones;
    }

    public String getTbaaddrs(){
        return tbaaddrs;
    }
    public void setTbaaddrs(String tbaaddrs){
        this.tbaaddrs=tbaaddrs;
    }

    public String getOtimes(){
        return otimes;
    }
    public void setOtimes(String otimes){
        this.otimes=otimes;
    }

    public String getOflags(){
        return oflags;
    }
    public void setOflags(String oflags){
        this.oflags=oflags;
    }

    public List<Tb_details> getTbdlist() {
        return tbdlist;
    }
    public void setTbdlist(List<Tb_details> tbdlist) {
        this.tbdlist = tbdlist;
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