package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import com.shops.bean.users;
import com.shops.utils.Dbhelper;

import com.wm.utils.DbConn;
import com.wm.utils.GetList;
import net.sf.json.JSONObject;


import com.shops.dao.UserDao;
import com.shops.bean.users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;


public class LoginSvlt extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        String uname = request.getParameter("uname");
        String upassword = request.getParameter("upassword");
        String utype = request.getParameter("utype");

        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();

        if ("tb_users".equals(utype)) {
            users user = userDao.findByPhoneAndPassword(uname, upassword);
            if (user != null) {
                json.put("msg", 1);
                session.setAttribute("utype", utype);
                session.setAttribute("myinfo", user);
            } else {
                json.put("msg", "账号或密码错误");
            }
        } else {
            json.put("msg", "不支持的用户类型");
        }

        response.getWriter().write(json.toString());
    }
}
