//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConn {
    Connection con = null;
    ResultSet rs = null;
    Statement stmt = null;

    public DbConn(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            this.con = DriverManager.getConnection(url, username, password);
            this.stmt = this.con.createStatement(1004, 1007);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet executeQuery(String sql) {
        try {
            this.rs = this.stmt.executeQuery(sql);
        } catch (SQLException er) {
            System.err.println(er.getMessage());
        }

        return this.rs;
    }

    public int executeUpdate(String sql) {
        int result = 0;

        try {
            result = this.stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }

    public boolean execute(String sql) {
        boolean result = false;

        try {
            result = this.stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Object getOnlyOne(String sql) {
        Object str = null;
        ResultSet rs = this.executeQuery(sql);

        try {
            if (rs.first()) {
                str = rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return str;
    }

    public boolean checkTrue(String sql) {
        ResultSet rs = this.executeQuery(sql);

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (this.con != null) {
                this.con.close();
            }
        } catch (Exception e) {
            System.out.print(e);
        }

        try {
            if (this.rs != null) {
                this.rs.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
