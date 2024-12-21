package com.shops.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.ResultSet;

import com.wm.utils.DbConn;
import com.wm.utils.HandlePage;
import com.shops.utils.Dbhelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wm.utils.GetList;
import com.shops.been.Tb_admins;
import com.shops.been.Tb_users;
import com.shops.been.Tb_addrs;
import com.shops.been.Tb_fgtypes;
import com.shops.been.Tb_sgtypes;
import com.shops.been.Tb_goods;
import com.shops.been.Tb_cars;
import com.shops.been.Tb_orders;
import com.shops.been.Tb_details;


public class PagingSvlt extends HttpServlet {

    public PagingSvlt() {
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
        //创建session对象
        HttpSession session=request.getSession();
        //创建数据库操作对象
        DbConn db=Dbhelper.getDb();
        //获取操作标识
        String tbname=request.getParameter("tbname");
        //获取信息id
        String flag=request.getParameter("flag");
        //根据查询语句，获取查询结果
        ResultSet rs=HandlePage.getContent(db, Integer.parseInt(flag), 10, session, "sql", "mysql");
        //如果是管理员信息管理，获取分页数据，跳转到管理员信息管理界面
        if("tb_admins_tb_admins".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_admins> alist=GetList.getlist(Tb_admins.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_admins.jsp").forward(request, response);
            return;
        }
        //如果是客户信息管理，获取分页数据，跳转到客户信息管理界面
        if("tb_admins_tb_users".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_users> alist=GetList.getlist(Tb_users.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_users.jsp").forward(request, response);
            return;
        }
        //如果是商品主类管理，获取分页数据，跳转到商品主类管理界面
        if("tb_admins_tb_fgtypes".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_fgtypes> alist=GetList.getlist(Tb_fgtypes.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_fgtypes.jsp").forward(request, response);
            return;
        }
        //如果是商品分类管理，获取分页数据，跳转到商品分类管理界面
        if("tb_admins_tb_sgtypes".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_sgtypes> alist=GetList.getlist(Tb_sgtypes.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //获取所属主类放入到request对象，以便前台页面，使用
            List<Tb_fgtypes> tb_fgtypeslist=GetList.getlist(Tb_fgtypes.class, db.executeQuery("select * from tb_fgtypes"));
            request.setAttribute("tb_fgtypeslist", tb_fgtypeslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_sgtypes.jsp").forward(request, response);
            return;
        }
        //如果是商品信息管理，获取分页数据，跳转到商品信息管理界面
        if("tb_admins_tb_goods".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_goods> alist=GetList.getlist(Tb_goods.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //获取所属分类放入到request对象，以便前台页面，使用
            List<Tb_sgtypes> tb_sgtypeslist=GetList.getlist(Tb_sgtypes.class, db.executeQuery("select * from tb_sgtypes"));
            request.setAttribute("tb_sgtypeslist", tb_sgtypeslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_goods.jsp").forward(request, response);
            return;
        }
        //如果是订单信息管理，获取分页数据，跳转到订单信息管理界面
        if("tb_admins_tb_orders".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_orders> blist=GetList.getlist(Tb_orders.class, rs);
            List<Tb_orders> alist=new ArrayList();
            Iterator<Tb_orders> bit=blist.iterator();
            while(bit.hasNext()){
                Tb_orders tbo=bit.next();
                List<Tb_details> tbdlist=GetList.getlist(Tb_details.class, db.executeQuery("select * from tb_details where onos='"+tbo.getOnos()+"'"));
                tbo.setTbdlist(tbdlist);
                alist.add(tbo);
            }
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_orders.jsp").forward(request, response);
            return;
        }
        //如果是订单详情管理，获取分页数据，跳转到订单详情管理界面
        if("tb_admins_tb_details".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_details> alist=GetList.getlist(Tb_details.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_details.jsp").forward(request, response);
            return;
        }
        //如果是收货地址管理，获取分页数据，跳转到收货地址管理界面
        if("tb_users_tb_addrs".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_addrs> alist=GetList.getlist(Tb_addrs.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //获取所属用户id放入到request对象，以便前台页面，使用
            List<Tb_users> tb_userslist=GetList.getlist(Tb_users.class, db.executeQuery("select * from tb_users"));
            request.setAttribute("tb_userslist", tb_userslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_addrs.jsp").forward(request, response);
            return;
        }
        //如果是购物车管理，获取分页数据，跳转到购物车管理界面
        if("tb_users_tb_cars".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_cars> alist=GetList.getlist(Tb_cars.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //获取客户id放入到request对象，以便前台页面，使用
            List<Tb_users> tb_userslist=GetList.getlist(Tb_users.class, db.executeQuery("select * from tb_users"));
            request.setAttribute("tb_userslist", tb_userslist);
            //获取商品id放入到request对象，以便前台页面，使用
            List<Tb_goods> tb_goodslist=GetList.getlist(Tb_goods.class, db.executeQuery("select * from tb_goods"));
            request.setAttribute("tb_goodslist", tb_goodslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_cars.jsp").forward(request, response);
            return;
        }
        //如果是订单信息管理，获取分页数据，跳转到订单信息管理界面
        if("tb_users_tb_orders".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_orders> blist=GetList.getlist(Tb_orders.class, rs);
            List<Tb_orders> alist=new ArrayList();
            Iterator<Tb_orders> bit=blist.iterator();
            while(bit.hasNext()){
                Tb_orders tbo=bit.next();
                List<Tb_details> tbdlist=GetList.getlist(Tb_details.class, db.executeQuery("select * from tb_details where onos='"+tbo.getOnos()+"'"));
                tbo.setTbdlist(tbdlist);
                alist.add(tbo);
            }
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_orders.jsp").forward(request, response);
            return;
        }
        //如果是订单详情管理，获取分页数据，跳转到订单详情管理界面
        if("tb_users_tb_details".equals(tbname)){
            //把查询结果集，转化成对象链表
            List<Tb_details> alist=GetList.getlist(Tb_details.class, rs);
            //数据放入到request对象
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_details.jsp").forward(request, response);
            return;
        }
    }

    public void init() throws ServletException {

    }
}
