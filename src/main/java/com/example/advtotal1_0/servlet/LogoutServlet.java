
package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.util.StaticData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        // 获取当前会话，如果存在则无效化
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (method.equals("adminLogout")) {
                session.removeAttribute(StaticData.ONLINE_ADMIN);
                response.sendRedirect(request.getContextPath() + "/jsp/other/login.jsp");
            }
           if (method.equals("advertiserLogout")) {
               session.removeAttribute(StaticData.ONLINE_ADVERTISER);
               response.sendRedirect(request.getContextPath() + "/jsp/other/login.jsp");
           }
           if (method.equals("webmasterLogout")) {
               session.removeAttribute(StaticData.ONLINE_WEBMASTER);
               response.sendRedirect(request.getContextPath() + "/jsp/other/login.jsp");
           }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
