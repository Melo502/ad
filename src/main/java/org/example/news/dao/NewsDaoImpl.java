package org.example.news.dao;

import java.sql.Connection;
import org.example.news.pojo.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao{
    @Override
    public List<News> getNewsList(Connection con) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<News> newsList = new ArrayList<>();
        if(con != null){
            String sql="select * from news order by date DESC ";
            Object[] params = {};
            try {
                rs = BaseDao.executeQuery(con, pstm, rs, sql, params);
                while (rs.next()) {
                    News news = new News();
                    news.setTitle(rs.getString("title"));
                    news.setContent(rs.getString("content"));
                    news.setDate(rs.getDate("date"));
                    newsList.add(news);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(con, pstm, rs);
            }
            //System.out.println(newsList);
        }
        return newsList;
    }
}
