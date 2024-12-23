package com.example.news.jdbc.dao;

import com.example.news.jdbc.common.BaseDao;
import com.example.news.jdbc.model.News;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class NewsDao extends BaseDao {

	// 添加新闻，若该新闻ID已被占用返回false,否则成功插入并返回true
	public boolean addNews(News news) throws SQLException {
		String checkSql = "SELECT * FROM news WHERE news_id = ?";
		ResultSet rs = executeQuery(checkSql, news.getNews_id());
		if (rs.next()) {
			return false; // 新闻ID已存在
		}

		String insertSql = "INSERT INTO news (news_id, title, over_view, content, publish_date, author, section_id, click, isHead, isImage, imageName, isHot) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowsAffected = executeUpdate(insertSql, news.getNews_id(), news.getTitle(), news.getOver_view(), news.getContent(),
				Timestamp.valueOf(news.getPublish_date()), news.getAuthor(), news.getSection_id(),
				news.getClick(), news.getIsHead(), news.getIsImage(), news.getImageName(), news.getIsHot());
		return rowsAffected > 0;
	}

	// 获取全部新闻
	public List<News> getAllNews() throws SQLException {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news ORDER BY publish_date DESC";
		ResultSet rs = executeQuery(sql);
		while (rs.next()) {
			News news = mapResultSetToNews(rs);
			newsList.add(news);
		}
		return newsList;
	}

	// 根据ID搜索新闻
	public News searchNewsById(int id) throws SQLException {
		String sql = "SELECT * FROM news WHERE news_id = ?";
		ResultSet rs = executeQuery(sql, id);
		if (rs.next()) {
			return mapResultSetToNews(rs);
		}
		return null;
	}

	// 根据完整标题搜索新闻
	public News searchNewsByFullTitle(String title) throws SQLException {
		String sql = "SELECT * FROM news WHERE title = ?";
		ResultSet rs = executeQuery(sql, title);
		if (rs.next()) {
			return mapResultSetToNews(rs);
		}
		return null;
	}

	// 根据标题包含内容搜索
	public List<News> searchNewsByPartTitle(String partTitle) throws SQLException {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news WHERE title LIKE ?";
		ResultSet rs = executeQuery(sql, "%" + partTitle + "%");
		while (rs.next()) {
			News news = mapResultSetToNews(rs);
			newsList.add(news);
		}
		return newsList;
	}

	// 搜索最新发布的十条新闻
	public List<News> searchNewsByDateTopTen() throws SQLException {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news ORDER BY publish_date DESC LIMIT 10";
		ResultSet rs = executeQuery(sql);
		while (rs.next()) {
			News news = mapResultSetToNews(rs);
			newsList.add(news);
		}
		return newsList;
	}

	// 搜索指定板块最新发布的十条新闻
	public List<News> searchNewsBySectionTopTen(int sectionId) throws SQLException {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news WHERE section_id = ? ORDER BY publish_date DESC LIMIT 10";
		ResultSet rs = executeQuery(sql, sectionId);
		while (rs.next()) {
			News news = mapResultSetToNews(rs);
			newsList.add(news);
		}
		return newsList;
	}

	// 搜索点击量最高的六条新闻
	public List<News> searchNewsByClickTopSix() throws SQLException {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news ORDER BY click DESC LIMIT 6";
		ResultSet rs = executeQuery(sql);
		while (rs.next()) {
			News news = mapResultSetToNews(rs);
			newsList.add(news);
		}
		return newsList;
	}

	// 修改新闻点击量
	public boolean updateClick(int id) throws SQLException {
		String sql = "SELECT * FROM news WHERE news_id = ?";
		ResultSet rs = executeQuery(sql, id);
		if (rs.next()) {
			int newClickCount = rs.getInt("click") + 1;
			String updateSql = "UPDATE news SET click = ? WHERE news_id = ?";
			int rowsAffected = executeUpdate(updateSql, newClickCount, id);
			return rowsAffected > 0;
		}
		return false;
	}

	// 根据ID删除新闻
	public boolean deleteNewsById(int id) throws SQLException {
		String sql = "SELECT * FROM news WHERE news_id = ?";
		ResultSet rs = executeQuery(sql, id);
		if (rs.next()) {
			String deleteSql = "DELETE FROM news WHERE news_id = ?";
			int rowsAffected = executeUpdate(deleteSql, id);
			return rowsAffected > 0;
		}
		return false;
	}

	// 将ResultSet映射到News对象
	private News mapResultSetToNews(ResultSet rs) throws SQLException {
		News news = new News();
		news.setNews_id(rs.getInt("news_id"));
		news.setTitle(rs.getString("title"));
		news.setOver_view(rs.getString("over_view"));
		news.setContent(rs.getString("content"));
		news.setPublish_date(rs.getString("publish_date"));
		news.setAuthor(rs.getString("author"));
		news.setSection_id(rs.getInt("section_id"));
		news.setClick(rs.getInt("click"));
		news.setIsHead(rs.getInt("isHead"));
		news.setIsImage(rs.getInt("isImage"));
		news.setImageName(rs.getString("imageName"));
		news.setIsHot(rs.getInt("isHot"));
		return news;
	}
}


