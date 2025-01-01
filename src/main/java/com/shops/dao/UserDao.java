package com.shops.dao;


import com.shops.bean.users;
import com.shops.utils.Dbhelper;
import com.wm.utils.DbConn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public users findByPhoneAndPassword(String phone, String password) {
        String query = "SELECT * FROM tb_users WHERE uphones = '" + phone + "' AND upwds = '" + password + "' AND isLogin = '1'";
        DbConn db = Dbhelper.getDb();
        ResultSet rs = null;

        try {
            rs = db.executeQuery(query);
            if (rs.next()) {
                return new users(
                        rs.getInt("id"),
                        rs.getString("uphones"),
                        rs.getString("upwds"),
                        rs.getString("unames"),
                        rs.getString("uaddrs"),
                        null,
                        null,
                        rs.getString("isLogin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
