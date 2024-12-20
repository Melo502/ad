package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.shops.been.Tb_admins;
import com.shops.been.Tb_users;
import com.shops.utils.Dbhelper;

import com.wm.utils.DbConn;
import com.wm.utils.GetList;
import net.sf.json.JSONObject;


public class LoginSvlt extends HttpServlet {

    public LoginSvlt() {
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
        //获取用户登录信息
        String uname=request.getParameter("uname");
        String upassword=request.getParameter("upassword");
        String utype=request.getParameter("utype");
        //创建json对象
        JSONObject json=new JSONObject();
        //创建数据库操作对象
        DbConn db=Dbhelper.getDb();
        //定义数据库操作语句
        String cksql="";
        //判断用户身份确定操作语句
        if("tb_admins".equals(utype)){
            cksql="select * from tb_admins where anos='"+uname+"' and apwds='"+upassword+"'";
        }
        if("tb_users".equals(utype)){
            cksql="select * from tb_users where uphones='"+uname+"' and upwds='"+upassword+"' and isLogin= '1'";
        }
        if("".equals(cksql)){
            json.put("msg", "系统错误");
        }else{
            //判断用户名和密码是否正确
            if(db.checkTrue(cksql)){
                json.put("msg", 1);
                //把用户类型信息写入到session对象
                session.setAttribute("utype", utype);
                if("tb_admins".equals(utype)){
                    List<Tb_admins> a=GetList.getlist(Tb_admins.class, db.executeQuery("select tb_admins.id,tb_admins.anos,tb_admins.apwds,tb_admins.anames,tb_admins.aphones from tb_admins where 1=1  and anos='"+uname+"' and apwds='"+upassword+"' "));
                    Tb_admins ad=a.get(0);
                    session.setAttribute("myinfo", ad);
                }
                if("tb_users".equals(utype)){
                    List<Tb_users> a=GetList.getlist(Tb_users.class, db.executeQuery("select tb_users.id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs from tb_users where 1=1  and uphones='"+uname+"' and upwds='"+upassword+"' "));
                    Tb_users ad=a.get(0);
                    session.setAttribute("myinfo", ad);
                }
            }else{
                json.put("msg", "账号或密码错误");
            }
        }
        PrintWriter out=response.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }

    public void init() throws ServletException {

    }
}
