package com.shops.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.wm.utils.DbConn;
import com.wm.utils.HandlePage;
import com.shops.utils.Dbhelper;
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


public class InitSvlt extends HttpServlet {

    public InitSvlt() {
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
        String flgs=request.getParameter("flgs");
        if("tb_admins_tb_admins".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_admins.id,tb_admins.anos,tb_admins.apwds,tb_admins.anames,tb_admins.aphones from tb_admins where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_admins> alist=GetList.getlist(Tb_admins.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_admins.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_users".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_users.id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_users.isLogin from tb_users where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_users> alist=GetList.getlist(Tb_users.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_users.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_fgtypes".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_fgtypes.id,tb_fgtypes.fgtname from tb_fgtypes where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_fgtypes> alist=GetList.getlist(Tb_fgtypes.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_fgtypes.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_sgtypes".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_sgtypes.id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname from tb_sgtypes,tb_fgtypes where 1=1  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id ");
            }
            //查询结果集转化成链表
            List<Tb_sgtypes> alist=GetList.getlist(Tb_sgtypes.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            List<Tb_fgtypes> tb_fgtypeslist=GetList.getlist(Tb_fgtypes.class, db.executeQuery("select * from tb_fgtypes"));
            request.setAttribute("tb_fgtypeslist", tb_fgtypeslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_sgtypes.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_goods".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id ");
            }
            //查询结果集转化成链表
            List<Tb_goods> alist=GetList.getlist(Tb_goods.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            List<Tb_sgtypes> tb_sgtypeslist=GetList.getlist(Tb_sgtypes.class, db.executeQuery("select * from tb_sgtypes"));
            request.setAttribute("tb_sgtypeslist", tb_sgtypeslist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_goods.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_orders".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_orders.id,tb_orders.onos,tb_orders.tb_users_id,tb_orders.tbanames,tb_orders.tbaphones,tb_orders.tbaaddrs,tb_orders.otimes,tb_orders.oflags from tb_orders where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_orders> blist=GetList.getlist(Tb_orders.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            List<Tb_orders> alist=new ArrayList();
            Iterator<Tb_orders> bit=blist.iterator();
            while(bit.hasNext()){
                Tb_orders tbo=bit.next();
                List<Tb_details> tbdlist=GetList.getlist(Tb_details.class, db.executeQuery("select * from tb_details where onos='"+tbo.getOnos()+"'"));
                tbo.setTbdlist(tbdlist);
                alist.add(tbo);
            }
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_orders.jsp").forward(request, response);
            return;
        }
        if("tb_admins_tb_details".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_details.id,tb_details.onos,tb_details.tb_goods_id,tb_details.gnames,tb_details.gpics,tb_details.gvals,tb_details.dnums from tb_details where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_details> alist=GetList.getlist(Tb_details.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_admins/mtb_details.jsp").forward(request, response);
            return;
        }
        if("tb_users_tb_users".equals(tbname)){
            //获取当前登录用户信息
            Object myinfo=session.getAttribute("myinfo");
            if(myinfo==null){
                return;
            }
            Tb_users user=(Tb_users)myinfo;
            //查询结果集转化成链表
            List<Tb_users> alist=GetList.getlist(Tb_users.class,db.executeQuery("select tb_users.id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs from tb_users where id="+user.getId()));
            //查询结果传到前台
            Tb_users ad=alist.get(0);
            request.setAttribute("tb_users", ad);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_users.jsp").forward(request, response);
            return;
        }
        if("tb_users_tb_addrs".equals(tbname)){
            //获取当前登录用户信息
            Object myinfo=session.getAttribute("myinfo");
            if(myinfo==null){
                return;
            }
            Tb_users user=(Tb_users)myinfo;
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select * from tb_addrs where tb_addrs.tb_users_id="+user.getId());
            }
            //查询结果集转化成链表
            List<Tb_addrs> alist=GetList.getlist(Tb_addrs.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_addrs.jsp").forward(request, response);
            return;
        }
        if("tb_users_tb_cars".equals(tbname)){
            //获取当前登录用户信息
            Object myinfo=session.getAttribute("myinfo");
            if(myinfo==null){
                return;
            }
            Tb_users user=(Tb_users)myinfo;
            //查询结果集转化成链表
            List<Tb_cars> alist=GetList.getlist(Tb_cars.class, db.executeQuery("select tb_cars.id,tb_cars.tb_users_id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_cars.tb_goods_id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gflags,tb_cars.cnums from tb_cars,tb_users,tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_cars.tb_users_id=tb_users.id  and tb_cars.tb_goods_id=tb_goods.id  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id and tb_users.id="+user.getId()));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            float allmoney=0;
            Iterator<Tb_cars> tbcit=alist.iterator();
            while(tbcit.hasNext()){
                Tb_cars tbc=tbcit.next();
                allmoney+=tbc.getCnums()*tbc.getGvals();
            }
            request.setAttribute("allmoney", allmoney);
            List<Tb_addrs> addrlist=GetList.getlist(Tb_addrs.class, db.executeQuery("select * from tb_addrs where tb_users_id="+user.getId()));
            request.setAttribute("addrlist", addrlist);
            //跳转到前台页面
            request.getRequestDispatcher("/cars.jsp").forward(request, response);
            return;
        }
        if("tb_users_tb_orders".equals(tbname)){
            //获取当前登录用户信息
            Object myinfo=session.getAttribute("myinfo");
            if(myinfo==null){
                return;
            }
            Tb_users user=(Tb_users)myinfo;
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_orders.id,tb_orders.onos,tb_orders.tb_users_id,tb_orders.tbanames,tb_orders.tbaphones,tb_orders.tbaaddrs,tb_orders.otimes,tb_orders.oflags from tb_orders where tb_users_id="+user.getId());
            }
            //查询结果集转化成链表
            List<Tb_orders> blist=GetList.getlist(Tb_orders.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            List<Tb_orders> alist=new ArrayList();
            Iterator<Tb_orders> bit=blist.iterator();
            while(bit.hasNext()){
                Tb_orders tbo=bit.next();
                List<Tb_details> tbdlist=GetList.getlist(Tb_details.class, db.executeQuery("select * from tb_details where onos='"+tbo.getOnos()+"'"));
                tbo.setTbdlist(tbdlist);
                alist.add(tbo);
            }
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_orders.jsp").forward(request, response);
            return;
        }
        if("tb_users_tb_details".equals(tbname)){
            //如果请求来自左侧菜单，查询全部内容
            if("1".equals(flgs)){
                session.setAttribute("sql", "select tb_details.id,tb_details.onos,tb_details.tb_goods_id,tb_details.gnames,tb_details.gpics,tb_details.gvals,tb_details.dnums from tb_details where 1=1 ");
            }
            //查询结果集转化成链表
            List<Tb_details> alist=GetList.getlist(Tb_details.class, HandlePage.getContent(db, 1, 10, session, "sql", "mysql"));
            //查询结果传到前台
            request.setAttribute("alist", alist);
            //跳转到前台页面
            request.getRequestDispatcher("/views/tb_users/mtb_details.jsp").forward(request, response);
            return;
        }
    }

    public void init() throws ServletException {

    }
}
