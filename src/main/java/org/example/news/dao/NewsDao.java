package org.example.news.dao;

import org.example.news.pojo.News;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface NewsDao {
    List<News> getNewsList(Connection con) throws SQLException;
}
