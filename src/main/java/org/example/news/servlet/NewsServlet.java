package org.example.news.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.news.pojo.News;
import org.example.news.service.NewsService;
import org.example.news.service.NewsServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/newsServlet.do")
public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<News> newsList;
        NewsService newsService = new NewsServiceImpl();
        newsList=newsService.getNewsList();
        System.out.println(newsList);
        req.setAttribute("newsList", newsList);
        req.getRequestDispatcher("view_news.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
