package com.example.advtotal1_0.dao.ad;



import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.pojo.AdType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdDaoImpl implements AdDao {

    @Override
    public int createAd(Ad ad) throws Exception {
        String sql = "INSERT INTO ad (advertiserId, title, description, imageUrl, url, typeId, status, typeKeyWords, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        Object[] params = {
                ad.getAdvertiserId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getImageUrl(),
                ad.getUrl(),
                ad.getTypeId(),
                "停用",
                ad.getTypeKeyWords(),
                ad.getPrice()
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
                ad.setPrice(rs.getDouble("price"));
            }
            return ad;
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
    }

    @Override
    public int updateAd(Ad ad) throws Exception {
        String sql = "UPDATE ad SET advertiserId = ?, title = ?, description = ?, imageUrl = ?, url = ?, typeId = ?, typeKeyWords = ?,price = ? WHERE id = ?";
        Object[] params = {
                ad.getAdvertiserId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getImageUrl(),
                ad.getUrl(),
                ad.getTypeId(),
                ad.getTypeKeyWords(),
                ad.getPrice(),
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
                ad.setPrice(rs.getDouble("price"));
                adList.add(ad);
            }
            return adList;
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
    }

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

            // 添加 PreparedStatement 的占位符
            for (int i = 0; i < adIds.size(); i++) {
                sql.append("?");
                if (i < adIds.size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");

            // 将 adIds 转换为 Object 数组作为参数
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
                ad.setPrice(rs.getDouble("price"));
                ads.add(ad);
            }
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return ads;
    }


    public List<Ad> findAdsByTypeKeyWords(String typeKeyWords) {
        List<Ad> adList = new ArrayList<>();
        String sql = "SELECT * FROM ad WHERE typeKeyWords = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            connection = BaseDao.getConnection(); // 获取数据库连接
            Object[] params = {typeKeyWords}; // 设置查询条件
            try {
                rs = BaseDao.executeQuery(connection, pstm, rs, sql, params); // 执行查询
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 处理查询结果
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
                ad.setPrice(rs.getDouble("price"));
                adList.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放数据库资源
            BaseDao.closeResource(connection, pstm, rs);
        }

        return adList;
    }

    @Override
    public int updateAdStatusBatch(List<Integer> adIds, String status) throws Exception {
        if (adIds == null || adIds.isEmpty()) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement pstm = null;
        int totalRows = 0;

        try {
            conn = BaseDao.getConnection();
            conn.setAutoCommit(false); // 开启事务

            StringBuilder sql = new StringBuilder("UPDATE ad SET status = ? WHERE id IN (");
            for (int i = 0; i < adIds.size(); i++) {
                sql.append("?");
                if (i < adIds.size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, status);
            for (int i = 0; i < adIds.size(); i++) {
                pstm.setInt(i + 2, adIds.get(i));
            }

            totalRows = pstm.executeUpdate();
            conn.commit(); // 提交事务
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback(); // 回滚事务
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // 恢复默认自动提交
            }
            BaseDao.closeResource(conn, pstm, null);
        }

        return totalRows;
    }

    @Override
    public List<Ad> searchAdsByTitle(String searchQuery) throws Exception {
        List<Ad> ads = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM ad WHERE title LIKE ? ORDER BY id DESC";
        String likeQuery = "%" + searchQuery + "%";

        try {
            conn = BaseDao.getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, new Object[]{likeQuery});
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
                ad.setPrice(rs.getDouble("price"));
                ads.add(ad);
            }
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return ads;
    }

    @Override
    public List<String> getAllAdTypes() throws Exception {
        List<String> adTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT typeKeyWords FROM ad ORDER BY typeKeyWords";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = BaseDao.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                adTypes.add(rs.getString("typeKeyWords"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取广告类型失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return adTypes;
    }

    @Override
    public List<Ad> getAdsByAdvertiserId(int advertiserId) throws Exception {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT * FROM ad WHERE advertiserId = ? ORDER BY id DESC";

        try (Connection conn = BaseDao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, advertiserId);
            try (ResultSet rs = pstm.executeQuery()) {
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
                    ad.setPrice(rs.getDouble("price"));
                    ads.add(ad);
                }
            }

        } catch (Exception e) {
            throw new Exception("根据广告主ID获取广告列表失败", e);
        }

        return ads;
    }
    @Override
    public List<String> getAllAdTypes(int advertiserId) throws Exception {
        List<String> adTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT typeKeyWords FROM ad WHERE advertiserId = ? ORDER BY typeKeyWords";

        try (Connection conn = BaseDao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, advertiserId); // TODO: 动态传递advertiserId
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    adTypes.add(rs.getString("typeKeyWords"));
                }
            }

        } catch (Exception e) {
            throw new Exception("获取广告类型失败", e);
        }

        return adTypes;
    }

    @Override
    public List<Ad> searchAdsByTitle(int advertiserId, String title) throws Exception {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT * FROM ad WHERE advertiserId = ? AND title LIKE ? ORDER BY id DESC";

        try (Connection conn = BaseDao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, advertiserId);
            pstm.setString(2, "%" + title + "%");
            try (ResultSet rs = pstm.executeQuery()) {
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
                    ad.setPrice(rs.getDouble("price"));
                    ads.add(ad);
                }
            }

        } catch (Exception e) {
            throw new Exception("根据广告主ID和标题搜索广告失败", e);
        }

        return ads;
    }

    @Override
    public List<Ad> getAdByTitle(String website, String searchTitle) throws Exception {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT a.id, a.title, a.description, a.imageUrl, a.url, a.typeKeyWords, a.status, a.price " +
                "FROM ad a " +
                "JOIN ad_placements ar ON a.id = ar.ad_id " +
                "WHERE ar.website = ? AND a.title LIKE ? " +
                "GROUP BY a.id, a.title, a.description, a.imageUrl, a.url, a.typeKeyWords, a.status, a.price";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {website, "%" + searchTitle + "%"};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                Ad ad = new Ad();
                ad.setId(rs.getInt("id"));
                ad.setTitle(rs.getString("title"));
                ad.setDescription(rs.getString("description"));
                ad.setImageUrl(rs.getString("imageUrl"));
                ad.setUrl(rs.getString("url"));
                ad.setTypeKeyWords(rs.getString("typeKeyWords"));
                ad.setStatus(rs.getString("status"));
                ad.setPrice(rs.getDouble("price"));
                ads.add(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("通过广告标题获取广告详细信息失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return ads;
    }

    @Override
    public List<Ad> getAllAdsByWebsite(String website) throws Exception {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT DISTINCT a.id, a.title, a.description, a.imageUrl, a.url, a.typeKeyWords, a.status, a.price " +
                "FROM ad a " +
                "JOIN ad_placements ar ON a.id = ar.ad_id " +
                "WHERE ar.website = ? ";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {website};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                Ad ad = new Ad();
                ad.setId(rs.getInt("id"));
                ad.setTitle(rs.getString("title"));
                ad.setDescription(rs.getString("description"));
                ad.setImageUrl(rs.getString("imageUrl"));
                ad.setUrl(rs.getString("url"));
                ad.setTypeKeyWords(rs.getString("typeKeyWords"));
                ad.setStatus(rs.getString("status"));
                ad.setPrice(rs.getDouble("price"));
                ads.add(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取所有发布在本网站的广告失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return ads;
    }

    @Override
    public List<String> getAllAdTypes(String website) throws Exception {
        List<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT a.typeKeyWords FROM ad a JOIN ad_placements ar ON a.id = ar.ad_id WHERE ar.website = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {website};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            while (rs.next()) {
                String type = rs.getString("typeKeyWords");
                if (type != null && !type.trim().isEmpty()) {
                    types.add(type.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取广告类型失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return types;
    }
}
