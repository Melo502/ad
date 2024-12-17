package com.example.advtotal1_0.dao.ad;



import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.Ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdDaoImpl implements AdDao {

    @Override
    public int createAd(Ad ad) throws Exception {
        String sql = "INSERT INTO ad (advertiserId, title, description, imageUrl, url, typeId, status, typeKeyWords) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
                ad.getAdvertiserId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getImageUrl(),
                ad.getUrl(),
                ad.getTypeId(),
                ad.getStatus(),
                ad.getTypeKeyWords()
        };
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            return BaseDao.execute(conn, pstm, sql, params);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public Ad getAdById(int id) throws Exception {
        String sql = "SELECT * FROM ad WHERE id = ?";
        Object[] params = {id};
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Ad ad = null;
        try {
            conn = BaseDao.getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            if (rs.next()) {
                ad = new Ad();
                ad.setId(rs.getInt("id"));
                ad.setAdvertiserId(rs.getInt("advertiserId"));
                ad.setTitle(rs.getString("title"));
                ad.setDescription(rs.getString("description"));
                ad.setImageUrl(rs.getString("imageUrl"));
                ad.setUrl(rs.getString("url"));
                ad.setTypeId(rs.getInt("typeId"));
                ad.setStatus(rs.getString("status"));
                ad.setTypeKeyWords(rs.getString("typeKeyWords"));
            }
            return ad;
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
    }

    @Override
    public int updateAd(Ad ad) throws Exception {
        String sql = "UPDATE ad SET advertiserId = ?, title = ?, description = ?, imageUrl = ?, url = ?, typeId = ?, status = ?, typeKeyWords = ? WHERE id = ?";
        Object[] params = {
                ad.getAdvertiserId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getImageUrl(),
                ad.getUrl(),
                ad.getTypeId(),
                ad.getStatus(),
                ad.getTypeKeyWords(),
                ad.getId()
        };
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            return BaseDao.execute(conn, pstm, sql, params);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public int deleteAd(int id) throws Exception {
        String sql = "DELETE FROM ad WHERE id = ?";
        Object[] params = {id};
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            return BaseDao.execute(conn, pstm, sql, params);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public List<Ad> getAllAds() throws Exception {
        String sql = "SELECT * FROM ad";
        Object[] params = {};
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Ad> adList = new ArrayList<>();
        try {
            conn = BaseDao.getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                Ad ad = new Ad();
                ad.setId(rs.getInt("id"));
                ad.setAdvertiserId(rs.getInt("advertiserId"));
                ad.setTitle(rs.getString("title"));
                ad.setDescription(rs.getString("description"));
                ad.setImageUrl(rs.getString("imageUrl"));
                ad.setUrl(rs.getString("url"));
                ad.setTypeId(rs.getInt("typeId"));
                ad.setStatus(rs.getString("status"));
                ad.setTypeKeyWords(rs.getString("typeKeyWords"));
                adList.add(ad);
            }
            return adList;
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
    }
    /**
     * 根据广告ID列表获取广告对象
     * @param adIds 广告ID列表
     * @return 广告对象列表
     * @throws Exception
     */
    public List<Ad> getAdsByIds(List<Integer> adIds) throws Exception {
        if (adIds == null || adIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Ad> ads = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = BaseDao.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM ad WHERE id IN (");
            for (int i = 0; i < adIds.size(); i++) {
                sql.append("?");
                if (i < adIds.size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(") AND status = '活跃'");

            // 将 adIds 转换为 Object 数组
            Object[] params = adIds.toArray(new Object[0]);
            rs = BaseDao.executeQuery(conn, pstm, rs, sql.toString(), params);

            while (rs.next()) {
                Ad ad = new Ad();
                ad.setId(rs.getInt("id"));
                ad.setAdvertiserId(rs.getInt("advertiserId"));
                ad.setTitle(rs.getString("title"));
                ad.setDescription(rs.getString("description"));
                ad.setImageUrl(rs.getString("imageUrl"));
                ad.setUrl(rs.getString("url"));
                ad.setTypeId(rs.getInt("typeId"));
                ad.setStatus(rs.getString("status"));
                ad.setTypeKeyWords(rs.getString("typeKeyWords"));
                ads.add(ad);
            }
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return ads;
    }
}
