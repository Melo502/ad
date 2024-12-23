package com.example.advtotal1_0.dao.adType;



import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.AdType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdTypeDaoImpl implements AdTypeDao {

    @Override
    public List<AdType> getAllAdTypes() throws Exception {
        String sql = "SELECT * FROM adtype";
        Object[] params = {};
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<AdType> typeList = new ArrayList<>();
        try {
            conn = BaseDao.getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                AdType type = new AdType();
                type.setId(rs.getInt("id"));
                type.setTypeName(rs.getString("name"));
                type.setCode(rs.getString("code"));
                typeList.add(type);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return typeList;
    }
}
