package com.example.advtotal1_0.dao.adRecord;

import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.AdRecord;
import com.example.advtotal1_0.service.Dto.AdDetailAggregation;
import com.example.advtotal1_0.service.Dto.OverallAggregation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.advtotal1_0.dao.common.BaseDao.getConnection;

public class AdRecordDaoImpl implements AdRecordDao {
    // 插入广告点击记录
    public int insertAdRecord(AdRecord adRecord) {
        String sql = "INSERT INTO adrecord (adId, income, clickTime, website) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        int result = 0;
        Object[] param = {adRecord.getAdId(), adRecord.getIncome(), adRecord.getClickTime(), adRecord.getWebsite()};
        connection = getConnection(); // 获取数据库连接
        // 执行更新
        try {
            result = BaseDao.execute(connection, pstmt, sql, param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            BaseDao.closeResource(connection, pstmt, null);
        }
        return result;
    }

    // 通过网站获取广告ID
    public List<Integer> getAdIdsByWebsite(String website) {
        String sql = "SELECT adId FROM adrecord WHERE website = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> adIds = new ArrayList<>();
        Object[] param = {website};
        try {
            connection = getConnection(); // 获取数据库连接

            try {
                rs = BaseDao.executeQuery(connection, pstmt, rs, sql, param); // 执行查询
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 将查询结果中的广告ID加入列表
            while (rs.next()) {
                adIds.add(rs.getInt("adId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            BaseDao.closeResource(connection, pstmt, rs);
        }
        return adIds;
    }

    @Override
    public List<AdRecord> getAllAdRecords() throws Exception {
        List<AdRecord> adRecords = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM adrecord ORDER BY clickTime DESC";

        try {
            conn = getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, new Object[]{});
            while (rs.next()) {
                AdRecord adRecord = new AdRecord();
                adRecord.setId(rs.getInt("id"));
                adRecord.setAdId(rs.getInt("adId"));
                adRecord.setIncome(rs.getDouble("income"));
                adRecord.setClickTime(rs.getTimestamp("clickTime"));
                adRecord.setWebsite(rs.getString("website"));
                adRecords.add(adRecord);
            }
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return adRecords;
    }

    @Override
    public List<AdRecord> getAdRecordsFiltered(java.util.Date startDate, java.util.Date endDate, Double minIncome, Double maxIncome, String website) throws Exception {
        List<AdRecord> adRecords = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder("SELECT * FROM adrecord WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (startDate != null) {
            sql.append(" AND clickTime >= ?");
            params.add(new java.sql.Timestamp(startDate.getTime()));
        }
        if (endDate != null) {
            sql.append(" AND clickTime <= ?");
            params.add(new java.sql.Timestamp(endDate.getTime()));
        }
        if (minIncome != null) {
            sql.append(" AND income >= ?");
            params.add(minIncome);
        }
        if (maxIncome != null) {
            sql.append(" AND income <= ?");
            params.add(maxIncome);
        }
        if (website != null && !website.trim().isEmpty()) {
            sql.append(" AND website LIKE ?");
            params.add("%" + website.trim() + "%");
        }

        sql.append(" ORDER BY clickTime DESC");

        try {
            conn = getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql.toString(), params.toArray());
            while (rs.next()) {
                AdRecord adRecord = new AdRecord();
                adRecord.setId(rs.getInt("id"));
                adRecord.setAdId(rs.getInt("adId"));
                adRecord.setIncome(rs.getDouble("income"));
                adRecord.setClickTime(rs.getTimestamp("clickTime"));
                adRecord.setWebsite(rs.getString("website"));
                adRecords.add(adRecord);
            }
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return adRecords;
    }


    @Override
    public List<String> getAllWebsites() throws Exception {
        List<String> websites = new ArrayList<>();
        String sql = "SELECT DISTINCT website FROM adrecord ORDER BY website";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = BaseDao.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                websites.add(rs.getString("website"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取网站列表失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }

        return websites;
    }

    @Override
    public List<AdDetailAggregation> getAdDetailAggregations0(String type, String website, Date startDate, Date endDate) throws Exception {
            List<AdDetailAggregation> aggregations = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a.id AS adId, a.title AS adTitle, COUNT(r.id) AS clickCount, SUM(r.income) AS totalIncome ");
            sql.append("FROM Ad a ");
            sql.append("JOIN adrecord r ON a.id = r.adId ");
            sql.append("WHERE 1=1 ");

            // 动态添加过滤条件
            if (type != null && !type.trim().isEmpty()) {
                sql.append("AND a.typeKeyWords = ? ");
            }
            if (website != null && !website.trim().isEmpty()) {
                sql.append("AND r.website = ? ");
            }
            if (startDate != null) {
                sql.append("AND r.clickTime >= ? ");
            }
            if (endDate != null) {
                sql.append("AND r.clickTime <= ? ");
            }

            sql.append("GROUP BY a.id, a.title ");
            sql.append("ORDER BY a.id, a.title");

            Connection conn = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;

            try {
                conn = BaseDao.getConnection();
                pstm = conn.prepareStatement(sql.toString());

                int paramIndex = 1;
                StringBuilder paramLog = new StringBuilder();
                if (type != null && !type.trim().isEmpty()) {
                    pstm.setString(paramIndex++, type);
                    paramLog.append("type=").append(type).append(", ");
                }
                if (website != null && !website.trim().isEmpty()) {
                    pstm.setString(paramIndex++, website);
                    paramLog.append("website=").append(website).append(", ");
                }
                if (startDate != null) {
                    pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
                    paramLog.append("startDate=").append(startDate).append(", ");
                }
                if (endDate != null) {
                    pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
                    paramLog.append("endDate=").append(endDate).append(", ");
                }

                // 打印 SQL 和参数
                System.out.println("Executing SQL: " + sql.toString());
                System.out.println("With Parameters: " + paramLog.toString());

                rs = pstm.executeQuery();
                while (rs.next()) {
                    AdDetailAggregation agg = new AdDetailAggregation();
                    agg.setAdId(rs.getInt("adId"));
                    agg.setAdTitle(rs.getString("adTitle"));
                    agg.setTotalClicks(rs.getInt("clickCount"));
                    agg.setTotalIncome(rs.getDouble("totalIncome"));
                    aggregations.add(agg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取广告详细聚合数据失败", e);
            } finally {
                BaseDao.closeResource(conn, pstm, rs);
            }
            System.out.println("agg: " + aggregations);
            return aggregations;
        }


        @Override
    public List<AdDetailAggregation> getAdDetailAggregationsByAdvertiserId(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception {
        List<AdDetailAggregation> aggregations = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id AS adId, a.title AS adTitle, COUNT(r.id) AS clickCount, SUM(r.income) AS totalIncome ");
        sql.append("FROM ad a ");
        sql.append("JOIN adrecord r ON a.id = r.adId ");
        sql.append("WHERE 1=1 ");

        // 动态添加过滤条件
        if(advertiserId != 0){
            sql.append("AND a.advertiserId = ? ");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append("AND a.typeKeyWords = ? ");
        }
        if (website != null && !website.trim().isEmpty()) {
            sql.append("AND r.website = ? ");
        }
        if (startDate != null) {
            sql.append("AND r.clickTime >= ? ");
        }
        if (endDate != null) {
            sql.append("AND r.clickTime <= ? ");
        }

        sql.append("GROUP BY a.id, a.title ");
        sql.append("ORDER BY a.id, a.title");

        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if(advertiserId != 0){
                pstm.setInt(paramIndex++, advertiserId);
            }
            if (type != null && !type.trim().isEmpty()) {
                pstm.setString(paramIndex++, type);
            }
            if (website != null && !website.trim().isEmpty()) {
                pstm.setString(paramIndex++, website);
            }
            if (startDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    AdDetailAggregation agg = new AdDetailAggregation();
                    agg.setAdId(rs.getInt("adId"));
                    agg.setAdTitle(rs.getString("adTitle"));
                    agg.setTotalClicks(rs.getInt("clickCount"));
                    agg.setTotalIncome(rs.getDouble("totalIncome"));
                    aggregations.add(agg);
                }
            }

        } catch (Exception e) {
            throw new Exception("获取广告详细聚合数据失败", e);
        }
        System.out.println("Dao.aggregations: " + aggregations);
        return aggregations;
    }

    @Override
    public OverallAggregation getOverallAggregationByAdvertiserId(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception {
        OverallAggregation overallAggregation = new OverallAggregation();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(r.id) AS totalClicks, SUM(r.income) AS totalIncome ");
        sql.append("FROM ad a ");
        sql.append("JOIN adrecord r ON a.id = r.adId ");
        sql.append("WHERE 1=1 ");

        // 动态添加过滤条件
        if(advertiserId != 0){
            sql.append("AND a.advertiserId = ? ");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append("AND a.typeKeyWords = ? ");
        }
        if (website != null && !website.trim().isEmpty()) {
            sql.append("AND r.website = ? ");
        }
        if (startDate != null) {
            sql.append("AND r.clickTime >= ? ");
        }
        if (endDate != null) {
            sql.append("AND r.clickTime <= ? ");
        }

        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if(advertiserId != 0){
                pstm.setInt(paramIndex++, advertiserId);
            }
            if (type != null && !type.trim().isEmpty()) {
                pstm.setString(paramIndex++, type);
            }
            if (website != null && !website.trim().isEmpty()) {
                pstm.setString(paramIndex++, website);
            }
            if (startDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
            }

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    overallAggregation.setTotalClicks(rs.getInt("totalClicks"));
                    overallAggregation.setTotalIncome(rs.getDouble("totalIncome"));
                }
            }

        } catch (Exception e) {
            throw new Exception("获取总体聚合数据失败", e);
        }
        System.out.println("Dao.overallAggregation: " + overallAggregation);
        return overallAggregation;
    }


    @Override
    public List<String> getAllWebsites(int advertiserId) throws Exception {
        List<String> websites = new ArrayList<>();
        String sql = "SELECT DISTINCT website FROM adrecord a JOIN ad ad ON a.adId = ad.id WHERE ad.advertiserId = ? ORDER BY website";

        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, advertiserId); // TODO: 动态传递advertiserId
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    websites.add(rs.getString("website"));
                }
            }

        } catch (Exception e) {
            throw new Exception("获取网站列表失败", e);
        }

        return websites;
    }

    @Override
    public List<AdRecord> getAdRecordsFiltered(int advertiserId, Date startDate, Date endDate, Double minIncome, Double maxIncome, String website) throws Exception {
        List<AdRecord> adRecords = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.* FROM adrecord r JOIN ad a ON r.adId = a.id ");
        sql.append("WHERE 1=1 ");

        // 动态添加过滤条件
        if(advertiserId !=0 ){
            sql.append("AND a.advertiserId = ? ");
        }
        if (startDate != null) {
            sql.append("AND r.clickTime >= ? ");
        }
        if (endDate != null) {
            sql.append("AND r.clickTime <= ? ");
        }
        if (minIncome != null) {
            sql.append("AND r.income >= ? ");
        }
        if (maxIncome != null) {
            sql.append("AND r.income <= ? ");
        }
        if (website != null && !website.trim().isEmpty()) {
            sql.append("AND r.website = ? ");
        }

        sql.append("ORDER BY r.clickTime DESC");

        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if(advertiserId !=0){
                pstm.setInt(paramIndex++, advertiserId);
            }
            if (startDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                pstm.setTimestamp(paramIndex++, new java.sql.Timestamp(endDate.getTime()));
            }
            if (minIncome != null) {
                pstm.setDouble(paramIndex++, minIncome);
            }
            if (maxIncome != null) {
                pstm.setDouble(paramIndex++, maxIncome);
            }
            if (website != null && !website.trim().isEmpty()) {
                pstm.setString(paramIndex++, website);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    AdRecord record = new AdRecord();
                    record.setId(rs.getInt("id"));
                    record.setAdId(rs.getInt("adId"));
                    record.setIncome(rs.getDouble("income"));
                    record.setClickTime(rs.getDate("clickTime"));
                    record.setWebsite(rs.getString("website"));
                    adRecords.add(record);
                }
            }

        } catch (Exception e) {
            throw new Exception("获取广告记录失败", e);
        }

        return adRecords;
    }

}

