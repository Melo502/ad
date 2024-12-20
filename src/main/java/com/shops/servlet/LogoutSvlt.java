package com.shops.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LogoutSvlt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LogoutSvlt() {
        super();
    }

    public void destroy() {
        super.destroy();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置传输数据编码方式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取session对象
        HttpSession session=request.getSession();
        //清除登录信息
        session.removeAttribute("utype");
        session.removeAttribute("myinfo");
        session.removeAttribute("sql");
        session.removeAttribute("syflag");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void init() throws ServletException {

    }
}
