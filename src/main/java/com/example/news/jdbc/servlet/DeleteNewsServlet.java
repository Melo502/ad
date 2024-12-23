package com.example.news.jdbc.servlet;

import com.example.news.jdbc.dao.NewsDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;


@WebServlet("/DeleteNewsServlet")
public class DeleteNewsServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("news_id");
		int news_id = Integer.parseInt(id);
		String b = request.getParameter("boo");
		NewsDao newsDao = new NewsDao();
		try {
			boolean bool = newsDao.deleteNewsById(news_id);
			if(bool){//成功
				HttpSession session = request.getSession();
				session.invalidate();
				request.setAttribute("info"," 删除成功！");
				request.setAttribute("info1",b);
				request.getRequestDispatcher("message_delete.jsp").forward(request, response);
			}else {//失败
				request.setAttribute("info"," 错误:删除失败！");
				request.setAttribute("info1",b);
				request.getRequestDispatcher("message_delete.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
