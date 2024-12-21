package com.example.advtotal1_0.util;

import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.service.ad.AdService;
import com.example.advtotal1_0.service.ad.AdServiceImpl;
import com.example.advtotal1_0.service.adRecord.AdRecordService;
import com.example.advtotal1_0.service.adRecord.AdRecordServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/setUserInterest")
public class SetUserInterestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置跨域的响应头
        setCORSHeaders(response);
        // 获取兴趣类型和兴趣值
        String interestType = request.getParameter("interestType");
        String interestValue = request.getParameter("interestValue");

        List<Integer> AdIds = new ArrayList<>();
        List<Ad> adList = new ArrayList<>();
        String website = (String) request.getSession().getAttribute("website");
        AdRecordService adRecordService =new AdRecordServiceImpl();
        try {
            AdIds = adRecordService.getAdIdsByWebsite(website);
            AdService adService = new AdServiceImpl();
            adList = adService.getAdsByIds(AdIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置 Cookie
        Cookie userInterestCookie = new Cookie("user_interest", interestValue);
        userInterestCookie.setDomain(".ad-platform.com");
        userInterestCookie.setPath("/");
        userInterestCookie.setMaxAge(60 * 60 * 24 * 30);  // 30天
        userInterestCookie.setSecure(true);  // 确保在 HTTPS 上设置
        userInterestCookie.setHttpOnly(true);  // 防止 JavaScript 访问

        // 设置 SameSite=None 以允许跨站点请求
        String cookieHeader = "user_interest=" + interestValue + "; Domain=.ad-platform.com; Path=/; Max-Age=2592000; Secure; HttpOnly; SameSite=None";

        // 添加 Set-Cookie 头到响应
        response.addHeader("Set-Cookie", cookieHeader);

        // 返回响应
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\":\"success\", \"message\":\"User interest has been set in cookie\"}");
    }

    // 设置跨域的响应头
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 允许所有域名跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // 允许跨域携带凭证
    }
}



