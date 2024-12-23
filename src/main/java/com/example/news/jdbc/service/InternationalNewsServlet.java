package com.example.news.jdbc.service;
import com.example.news.jdbc.dao.NewsDao;
import com.example.news.jdbc.model.News;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/internationalNews")
public class InternationalNewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsDao newsDao = new NewsDao();

        // 查询国际新闻的前10条
        List<News> list = null;
        try {
            list = newsDao.searchNewsBySectionTopTen(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null;
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 将查询结果设置到 request 中
        request.setAttribute("newsList", list);
        request.setAttribute("clickList", clickList);

        // 请求转发到 JSP 页面
        request.getRequestDispatcher("index3.jsp").forward(request, response);
    }
}

