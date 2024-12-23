package com.example.advtotal1_0.dao.adPlacement;

import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.AdPlacement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdPlacementDaoImpl implements AdPlacementDao {
    @Override
    public boolean saveAdPlacement(AdPlacement adPlacement) {
        String sql = "INSERT INTO ad_placements (ad_id, website, user_id, placed_at) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {adPlacement.getAdId(), adPlacement.getWebsite(), adPlacement.getUserId(), adPlacement.getPlacedAt()};
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
            int rows = pstm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public String getLatestTargetWebsiteByAdId(int adId) {
        String sql = "SELECT website FROM ad_placements WHERE ad_id = ? ORDER BY placed_at DESC LIMIT 1";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {adId};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            if (rs.next()) {
                return rs.getString("website");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return null;
    }

    @Override
    public List<AdPlacement> getAdPlacementsByAdId(int adId) {
        List<AdPlacement> placements = new ArrayList<>();
        String sql = "SELECT * FROM ad_placements WHERE ad_id = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {adId};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                AdPlacement placement = new AdPlacement();
                placement.setId(rs.getInt("id"));
                placement.setAdId(rs.getInt("ad_id"));
                placement.setWebsite(rs.getString("website"));
                placement.setUserId(rs.getInt("user_id"));
                placement.setPlacedAt(rs.getTimestamp("placed_at"));
                placements.add(placement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return placements;
    }
}
