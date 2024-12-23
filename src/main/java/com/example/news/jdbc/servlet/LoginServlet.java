package com.example.news.jdbc.servlet;

import java.io.IOException;
import java.io.Serial;

import com.example.news.jdbc.dao.UserDao;
import com.example.news.jdbc.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDao userDao = new UserDao();
		User user = new User();
		try {
			boolean bool = userDao.login(username, password);
			//判断是否登录成功
			if(bool){//成功
				user.setUser_id(username);
				user.setPassword(password);
				request.getSession().setAttribute("admin", user);//将管理员对象放到session中
				//转发到admin.jsp中
				request.getRequestDispatcher("admin.jsp").forward(request, response);
			}else {//失败
				request.setAttribute("info"," 错误:用户名或密码错误！");
				request.getRequestDispatcher("message.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
