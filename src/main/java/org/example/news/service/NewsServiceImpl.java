package org.example.news.service;

import org.example.news.dao.BaseDao;
import org.example.news.dao.NewsDao;
import org.example.news.dao.NewsDaoImpl;
import org.example.news.pojo.News;

import java.sql.Connection;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao;
    public NewsServiceImpl() {
        newsDao = new NewsDaoImpl();
    }
    @Override
    public List<News> getNewsList() {
        Connection conn = null;
        List<News> newsList ;
        try {
            conn = BaseDao.getConnection();
            //System.out.println(conn);
            newsList = newsDao.getNewsList(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(conn,null,null);
        }
        return newsList;
    }
}
