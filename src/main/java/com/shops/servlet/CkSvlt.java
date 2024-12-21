package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.json.JSONObject;
public class CkSvlt extends HttpServlet {

    public CkSvlt() {
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
        //创建json对象
        JSONObject json=new JSONObject();
        //获取用户操作语句
        String sql=request.getParameter("sql");
        if(!"".equals(sql)){
            //把操作语句写入session
            session.setAttribute("sql", sql);
            json.put("msg", 1);
        }else{
            json.put("msg", "出错了");
        }
        PrintWriter out=response.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }

    public void init() throws ServletException {

    }
}
