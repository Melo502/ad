package com.example.advtotal1_0.dao.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的基类--静态类
 * @author Administrator
 *
 */
public class BaseDao {

    static{//静态代码块,在类加载的时候执行
        init();
    }

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    //初始化连接参数,从配置文件里获得
    public static void init(){
        Properties params=new Properties();
        String configFile = "dataBase.properties";
        //class方法反射回到父类获得加载方法再转变为资源流
        InputStream is=BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            params.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver=params.getProperty("driver");
        url=params.getProperty("url");
        user=params.getProperty("username");
        password=params.getProperty("password");

    }


    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(connection != null){System.out.println("连接成功");}
        return connection;
    }
    /**
     * 查询操作
     * @param connection
     * @param pstm
     * @param rs
     * @param sql
     * @param params
     * @return
     */
    public static ResultSet executeQuery(Connection connection,PreparedStatement pstm,ResultSet rs,
                                    String sql,Object[] params) throws Exception{
        pstm = connection.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            //数组的下标是从0开始 PreparedStatement占位符是从1开始
            pstm.setObject(i+1, params[i]);
        }
        //数据库查询
        rs = pstm.executeQuery();
        return rs;
    }
    /**
     * @param connection
     * @param pstm
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public static int execute(Connection connection,PreparedStatement pstm,
                              String sql,Object[] params) throws Exception{
        int updateRows = 0;
        pstm = connection.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            //数组的下标是从0开始 PreparedStatement占位符是从1开始
            pstm.setObject(i+1, params[i]);
        }
        //数据库更新
        updateRows = pstm.executeUpdate();
        return updateRows;
    }

    /**
     * 释放资源
     * @param connection
     * @param pstm
     * @param rs
     * @return
     */
    public static boolean closeResource(Connection connection,PreparedStatement pstm,ResultSet rs){
        boolean flag = true;
        if(rs != null){
            try {
                rs.close();
                rs = null;//GC回收
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(pstm != null){
            try {
                pstm.close();
                pstm = null;//GC回收
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(connection != null){
            try {
                connection.close();
                connection = null;//GC回收
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

}
