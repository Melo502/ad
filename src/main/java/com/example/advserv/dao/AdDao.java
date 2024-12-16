package com.example.advserv.dao;

import com.example.advserv.entity.Ad;
import java.sql.*;
import java.util.List;

public class AdDao extends GenericDao<Ad> {

    public AdDao(Connection connection) {
        super(connection);
    }

    // 插入广告
    public int insertAd(Ad ad) throws SQLException {
        String sql = "INSERT INTO ads (title, description, ad_image_url, ad_link, ad_category, keywords, start_time, end_time, target_location, budget, cost_per_click, cost_per_impression, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, ad.getTitle(), ad.getDescription(), ad.getAdImageUrl(), ad.getAdLink(),
                ad.getAdCategory(), ad.getKeywords(), ad.getStartTime(), ad.getEndTime(),
                ad.getTargetLocation(), ad.getBudget(), ad.getCostPerClick(), ad.getCostPerImpression(), ad.getStatus());
    }

    // 更新广告信息
    public int updateAd(Ad ad) throws SQLException {
        String sql = "UPDATE ads SET title = ?, description = ?, ad_image_url = ?, ad_link = ?, ad_category = ?, keywords = ?, start_time = ?, end_time = ?, target_location = ?, " +
                "budget = ?, cost_per_click = ?, cost_per_impression = ?, status = ? , admaster = ? WHERE id = ?";
        return update(sql, ad.getTitle(), ad.getDescription(), ad.getAdImageUrl(), ad.getAdLink(),
                ad.getAdCategory(), ad.getKeywords(), ad.getStartTime(), ad.getEndTime(),
                ad.getTargetLocation(), ad.getBudget(), ad.getCostPerClick(), ad.getCostPerImpression(),
                ad.getStatus(),ad.getAdmaster(), ad.getId());
    }

    // 根据广告ID查询广告
    public Ad findAdById(int id) throws SQLException {
        String sql = "SELECT * FROM ads WHERE id = ?";
        return findOne(sql, new RowMapper<Ad>() {
            @Override
            public Ad mapRow(ResultSet resultSet) throws SQLException {
                return mapAd(resultSet);
            }
        }, id);
    }

    // 查询所有广告
    public List<Ad> findAllAds() throws SQLException {
        String sql = "SELECT * FROM ads";
        return findAll(sql, new RowMapper<Ad>() {
            @Override
            public Ad mapRow(ResultSet resultSet) throws SQLException {
                return mapAd(resultSet);
            }
        });
    }

    // 根据状态查询广告
    public List<Ad> findAdsByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM ads WHERE status = ?";
        return findAll(sql, new RowMapper<Ad>() {
            @Override
            public Ad mapRow(ResultSet resultSet) throws SQLException {
                return mapAd(resultSet);
            }
        }, status);
    }

    // 根据广告ID删除广告
    public int deleteAd(int id) throws SQLException {
        String sql = "DELETE FROM ads WHERE id = ?";
        return delete(sql, id);
    }

    // 根据广告主用户名查询该广告主的所有广告
    public List<Ad> findAdsByAdmaster(String admaster) throws SQLException {
        String sql = "SELECT * FROM ad WHERE admaster = ?";
        return findAll(sql, new RowMapper<Ad>() {
            @Override
            public Ad mapRow(ResultSet resultSet) throws SQLException {
                Ad ad = new Ad();
                ad.setId(resultSet.getInt("id"));
                ad.setTitle(resultSet.getString("title"));
                ad.setAdCategory(resultSet.getString("adCategory"));
                ad.setDescription(resultSet.getString("description"));
                ad.setAdImageUrl(resultSet.getString("adImageUrl"));
                ad.setAdLink(resultSet.getString("adLink"));
                ad.setKeywords(resultSet.getString("keywords"));
                ad.setStartTime(resultSet.getTimestamp("startTime").toLocalDateTime());
                ad.setEndTime(resultSet.getTimestamp("endTime").toLocalDateTime());
                ad.setTargetLocation(resultSet.getString("targetLocation"));
                ad.setBudget(resultSet.getDouble("budget"));
                ad.setCostPerClick(resultSet.getDouble("costPerClick"));
                ad.setCostPerImpression(resultSet.getDouble("costPerImpression"));
                ad.setClickCount(resultSet.getInt("clickCount"));
                ad.setImpressionCount(resultSet.getInt("impressionCount"));
                ad.setTotalSpent(resultSet.getDouble("totalSpent"));
                ad.setStatus(resultSet.getString("status"));
                ad.setAdmaster(resultSet.getString("admaster"));
                return ad;
            }
        }, admaster);
    }

    // 将结果集映射为 Ad 实体对象
    private Ad mapAd(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        String adImageUrl = resultSet.getString("ad_image_url");
        String adLink = resultSet.getString("ad_link");
        String adCategory = resultSet.getString("ad_category");
        String keywords = resultSet.getString("keywords");
        Timestamp startTime = resultSet.getTimestamp("start_time");
        Timestamp endTime = resultSet.getTimestamp("end_time");
        String targetLocation = resultSet.getString("target_location");
        double budget = resultSet.getDouble("budget");
        double costPerClick = resultSet.getDouble("cost_per_click");
        double costPerImpression = resultSet.getDouble("cost_per_impression");
        int clickCount = resultSet.getInt("click_count");
        int impressionCount = resultSet.getInt("impression_count");
        double totalSpent = resultSet.getDouble("total_spent");
        String status = resultSet.getString("status");
        String admaster = resultSet.getString("admaster");

        return new Ad(id, title, description, adImageUrl, adLink, adCategory, keywords,
                startTime.toLocalDateTime(), endTime.toLocalDateTime(), targetLocation,
                budget, costPerClick, costPerImpression, status,admaster);
    }
}
