package com.example.advtotal1_0.filter;

import com.example.advtotal1_0.pojo.User;
import com.example.advtotal1_0.util.StaticData;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("*.do")
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestURI = req.getRequestURI();

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        if (isLoginRequest(requestURI) || isRegisterRequest(requestURI) || isGetAdContent(requestURI,req) || isPostAd(requestURI,req)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        String requiredRole = getRequiredRole(requestURI);

        
        if (requiredRole == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 根据需要的角色，从会话中获取对应的用户对象
        User user = getUserFromSession(req, requiredRole);
        
        if (user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(req.getContextPath() + "/jsp/error/404.jsp");
        }
    }

    private boolean isLoginRequest(String requestURI) {
        return requestURI.endsWith("/login.do");
    }
    
    private boolean isRegisterRequest(String requestURI) {
        return requestURI.endsWith("/register.do");
    }
    
    private String getRequiredRole(String requestURI) {
        if (requestURI.contains("/adminServlet.do")) {
            return "admin";
        } else if (requestURI.contains("/webmasterServlet.do")) {
            return "webmaster";
        } else if (requestURI.contains("/advertiserServlet.do")) {
            return "advertiser";
        }
        return null;
    }
    
    private User getUserFromSession(HttpServletRequest req, String role) {
        switch (role) {
            case "admin":
                return (User) req.getSession().getAttribute(StaticData.ONLINE_ADMIN);
            case "webmaster":
                return (User) req.getSession().getAttribute(StaticData.ONLINE_WEBMASTER);
            case "advertiser":
                return (User) req.getSession().getAttribute(StaticData.ONLINE_ADVERTISER);
            default:
                return null;
        }
    }

    private boolean isPostAd(String requestURI , HttpServletRequest req) {
        return requestURI.endsWith("/advertiserServlet.do") && req.getParameter("method").equals("postAd");
    }

    private boolean isGetAdContent(String requestURI, HttpServletRequest req) {
        return requestURI.endsWith("/advertiserServlet.do") && req.getParameter("method").equals("getAdContent");
    }
}
