package com.example.news.jdbc.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/setUserInterest")
public class SetUserInterestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置跨域的响应头
        setCORSHeaders(response);
        // 获取兴趣类型和兴趣值
        //String interestType = request.getParameter("interestType");
        String interestValue = request.getParameter("interestValue");
        if(interestValue == null || interestValue.equals("")){
            interestValue="金融";
        }
        // 设置 Cookie
        Cookie userInterestCookie = new Cookie("user_interest", interestValue);
        //userInterestCookie.setDomain(".ad-platform.com");
        userInterestCookie.setPath("/");
        userInterestCookie.setMaxAge(60 * 60 * 24 * 30);  // 30天
        //userInterestCookie.setSecure(true);  // 确保在 HTTPS 上设置
        //userInterestCookie.setHttpOnly(true);  // 防止 JavaScript 访问

        // 设置 SameSite=None 以允许跨站点请求
        String cookieHeader = "user_interest=" + interestValue + ";Path=/; Max-Age=2592000;  SameSite=Lax"; // Domain=.115.120.224.202;

        // 添加 Set-Cookie 头到响应
        response.addHeader("Set-Cookie", cookieHeader);


        response.addCookie(userInterestCookie);
        // 返回响应
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\":\"success\", \"message\":\"User interest has been set in cookie\"}");
    }

    // 处理 GET 请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置跨域的响应头
        setCORSHeaders(response);
        // 获取兴趣类型和兴趣值
        //String interestType = request.getParameter("interestType");
        String interestValue = request.getParameter("interestValue");


        // 设置 Cookie
        Cookie userInterestCookie = new Cookie("user_interest", interestValue);
        userInterestCookie.setPath("/");  // 设置 Cookie 的路径
        userInterestCookie.setMaxAge(60 * 60 * 24 * 30);  // 30天
        //userInterestCookie.setDomain("http://115.120.224.202/");
        // 设置 SameSite=None 以允许跨站点请求
        String cookieHeader = "user_interest=" + interestValue + ";Path=/; Max-Age=2592000; SameSite=Lax";

        // 添加 Set-Cookie 头到响应
        response.addHeader("Set-Cookie", cookieHeader);
        response.addCookie(userInterestCookie);
        // 返回响应
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\":\"success\", \"message\":\"User interest has been set in cookie\"}");
    }
    // 设置跨域的响应头
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://115.120.224.202:8824");  // 允许所有域名跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // 允许跨域携带凭证
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 确保处理 OPTIONS 请求时设置了 CORS 响应头
        response.setHeader("Access-Control-Allow-Origin", "http://115.120.224.202:8824");  // 设置允许的来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");  // 允许的方法
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");  // 允许的请求头
        response.setHeader("Access-Control-Allow-Credentials", "true");  // 允许跨域携带凭证
        response.setStatus(HttpServletResponse.SC_OK);  // 返回 200 OK
    }

}



