package com.example.news.jdbc.servlet;

import com.example.news.jdbc.dao.NewsDao;
import com.example.news.jdbc.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;


@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("news_id");
		int news_id = Integer.parseInt(id);
		NewsDao newsDao = new NewsDao();
		News news;
		try {
			news = newsDao.searchNewsById(news_id);
			//判断新闻是否查询成功
			if(news != null){//成功
				request.getSession().setAttribute("news", news);//将管理员对象放到session中
				request.getRequestDispatcher("dnews_id.jsp").forward(request, response);
			}else {//失败
				request.setAttribute("info"," 错误:没有此新闻！");
				request.getRequestDispatcher("message_search.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
