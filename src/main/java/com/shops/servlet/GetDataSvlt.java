package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.wm.utils.DbConn;
import com.shops.utils.Dbhelper;
import net.sf.json.JSONObject;
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

public class GetDataSvlt extends HttpServlet {

    public GetDataSvlt() {
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
        //创建json对象
        JSONObject json=new JSONObject();
        //创建数据库操作对象
        DbConn db=Dbhelper.getDb();
        //获取操作标识
        String tbname=request.getParameter("tbname");
        //获取信息id
        String id=request.getParameter("id");
        if("tb_admins_tb_admins".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_admins> alist=GetList.getlist(Tb_admins.class, db.executeQuery("select tb_admins.id,tb_admins.anos,tb_admins.apwds,tb_admins.anames,tb_admins.aphones from tb_admins where 1=1  and tb_admins.id="+id));
            //创建对象
            Tb_admins a=new Tb_admins();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_users".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_users> alist=GetList.getlist(Tb_users.class, db.executeQuery("select tb_users.id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs from tb_users where 1=1  and tb_users.id="+id));
            //创建对象
            Tb_users a=new Tb_users();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_fgtypes".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_fgtypes> alist=GetList.getlist(Tb_fgtypes.class, db.executeQuery("select tb_fgtypes.id,tb_fgtypes.fgtname from tb_fgtypes where 1=1  and tb_fgtypes.id="+id));
            //创建对象
            Tb_fgtypes a=new Tb_fgtypes();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_sgtypes".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_sgtypes> alist=GetList.getlist(Tb_sgtypes.class, db.executeQuery("select tb_sgtypes.id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname from tb_sgtypes,tb_fgtypes where 1=1  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  and tb_sgtypes.id="+id));
            //创建对象
            Tb_sgtypes a=new Tb_sgtypes();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_goods".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_goods> alist=GetList.getlist(Tb_goods.class, db.executeQuery("select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  and tb_goods.id="+id));
            //创建对象
            Tb_goods a=new Tb_goods();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_orders".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_orders> alist=GetList.getlist(Tb_orders.class, db.executeQuery("select tb_orders.id,tb_orders.onos,tb_orders.tb_users_id,tb_orders.tbanames,tb_orders.tbaphones,tb_orders.tbaaddrs,tb_orders.otimes,tb_orders.oflags from tb_orders where 1=1  and tb_orders.id="+id));
            //创建对象
            Tb_orders a=new Tb_orders();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_admins_tb_details".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_details> alist=GetList.getlist(Tb_details.class, db.executeQuery("select tb_details.id,tb_details.onos,tb_details.tb_goods_id,tb_details.gnames,tb_details.gpics,tb_details.gvals,tb_details.dnums from tb_details where 1=1  and tb_details.id="+id));
            //创建对象
            Tb_details a=new Tb_details();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_users_tb_addrs".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_addrs> alist=GetList.getlist(Tb_addrs.class, db.executeQuery("select tb_addrs.id,tb_addrs.tb_users_id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_addrs.tbaphones,tb_addrs.tbanames,tb_addrs.tbaaddrs from tb_addrs,tb_users where 1=1  and tb_addrs.tb_users_id=tb_users.id  and tb_addrs.id="+id));
            //创建对象
            Tb_addrs a=new Tb_addrs();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_users_tb_cars".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_cars> alist=GetList.getlist(Tb_cars.class, db.executeQuery("select tb_cars.id,tb_cars.tb_users_id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_cars.tb_goods_id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gflags,tb_cars.cnums from tb_cars,tb_users,tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_cars.tb_users_id=tb_users.id  and tb_cars.tb_goods_id=tb_goods.id  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  and tb_cars.id="+id));
            //创建对象
            Tb_cars a=new Tb_cars();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_users_tb_orders".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_orders> alist=GetList.getlist(Tb_orders.class, db.executeQuery("select tb_orders.id,tb_orders.onos,tb_orders.tb_users_id,tb_orders.tbanames,tb_orders.tbaphones,tb_orders.tbaaddrs,tb_orders.otimes,tb_orders.oflags from tb_orders where 1=1  and tb_orders.id="+id));
            //创建对象
            Tb_orders a=new Tb_orders();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        if("tb_users_tb_details".equals(tbname)){
            //查询数据库获取想要的信息
            List<Tb_details> alist=GetList.getlist(Tb_details.class, db.executeQuery("select tb_details.id,tb_details.onos,tb_details.tb_goods_id,tb_details.gnames,tb_details.gpics,tb_details.gvals,tb_details.dnums from tb_details where 1=1  and tb_details.id="+id));
            //创建对象
            Tb_details a=new Tb_details();
            //取查询结果给对象
            if(alist.size()>0){
                a=alist.get(0);
            }
            //把信息写入json
            json.put("ob", a);
        }
        PrintWriter out=response.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }

    public void init() throws ServletException {

    }
}
