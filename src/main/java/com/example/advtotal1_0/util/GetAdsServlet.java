package com.example.advtotal1_0.util;


import com.example.advtotal1_0.dao.ad.AdDao;
import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.service.ad.AdService;
import com.example.advtotal1_0.service.ad.AdServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/getAds")
public class GetAdsServlet extends HttpServlet {
    private AdService adService = new AdServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        // 设置跨域的响应头
        setCORSHeaders(response);

        // 获取 Cookie 中的用户兴趣
        String userInterest = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("user_interest".equals(cookie.getName())) {
                userInterest = cookie.getValue();
                break;
            }
        }

        if (userInterest != null) {
            // 根据用户兴趣获取广告
            List<Ad> ads = null;  //等待完善
            try {
                ads = adService.getAdsByTypeKeyWords(userInterest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(ads);
            response.getWriter().write(jsonResponse);
        } else {
            response.getWriter().write("{\"error\":\"没有找到相关广告\"}");
        }
    }

    // 设置跨域的响应头
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");  // 允许所有域名跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // 允许跨域携带凭证
    }
}
