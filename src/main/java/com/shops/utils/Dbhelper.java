package com.shops.utils;

import com.wm.utils.DbConn;

public class Dbhelper {
    public static DbConn getDb() {
        String url="jdbc:mysql://localhost:3306/shopping?characterEncoding=utf-8";
        String driver="com.mysql.cj.jdbc.Driver";
        String uname="root";
        String upassword="cheche";
        return new DbConn(driver,url,uname,upassword);
    }
}
