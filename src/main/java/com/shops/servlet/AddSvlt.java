package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.wm.utils.DbConn;
import com.wm.utils.GetList;
import com.shops.been.Tb_addrs;
import com.shops.been.Tb_cars;
import com.shops.been.Tb_users;
import com.shops.utils.Dbhelper;

import net.sf.json.JSONObject;

public class AddSvlt extends HttpServlet {

    public AddSvlt() {
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
        //管理员信息添加
        if("tb_admins_tb_admins".equals(tbname)){
            //获取前台提交数据
            String anos=request.getParameter("anos");
            String apwds=request.getParameter("apwds");
            String anames=request.getParameter("anames");
            String aphones=request.getParameter("aphones");
            //账号信息唯一性验证
            if(db.checkTrue("select * from tb_admins where anos='"+anos+"'")){
                json.put("msg", "账号已存在");
            }else{
                //tb_admins信息录入数据库
                if(db.executeUpdate("insert into tb_admins(anos,apwds,anames,aphones) values('"+anos+"','"+apwds+"','"+anames+"','"+aphones+"')")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }
        //商品主类添加
        if("tb_admins_tb_fgtypes".equals(tbname)){
            //获取前台提交数据
            String fgtname=request.getParameter("fgtname");
            //类型名称信息唯一性验证
            if(db.checkTrue("select * from tb_fgtypes where fgtname='"+fgtname+"'")){
                json.put("msg", "类型名称已存在");
            }else{
                //tb_fgtypes信息录入数据库
                if(db.executeUpdate("insert into tb_fgtypes(fgtname) values('"+fgtname+"')")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }
        //商品分类添加
        if("tb_admins_tb_sgtypes".equals(tbname)){
            //获取前台提交数据
            String sgtnames=request.getParameter("sgtnames");
            String tb_fgtypes_id=request.getParameter("tb_fgtypes_id");
            //分类名称信息唯一性验证
            if(db.checkTrue("select * from tb_sgtypes where sgtnames='"+sgtnames+"'")){
                json.put("msg", "分类名称已存在");
            }else{
                //tb_sgtypes信息录入数据库
                if(db.executeUpdate("insert into tb_sgtypes(sgtnames,tb_fgtypes_id) values('"+sgtnames+"',"+tb_fgtypes_id+")")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }
        //用户注册
        if("tb_users_tb_users".equals(tbname)){
            //获取前台提交数据
            String upwds=request.getParameter("upwds");
            String uphones=request.getParameter("uphones");
            String unames=request.getParameter("unames");
            String uaddrs=request.getParameter("uaddrs");
            String isLogin = request.getParameter("isLogin");
            if(db.checkTrue("select id from tb_users where uphones='"+uphones+"'")){
                json.put("msg", "电话已注册");
            }else{
                //tb_users信息录入数据库
                if(db.executeUpdate("insert into tb_users(uphones,unames,uaddrs,upwds,isLogin) values('"+uphones+"','"+unames+"','"+uaddrs+"','"+upwds+"','"+isLogin+"')")>0){
                    json.put("msg", "注册成功");
                }else{
                    json.put("msg", "注册失败");
                }
            }
        }
        //收货地址添加
        if("tb_users_tb_addrs".equals(tbname)){
            Object myinfo=request.getSession().getAttribute("myinfo");
            if(myinfo==null){
                json.put("msg", "请重新登录");
            }else{
                Tb_users user=(Tb_users)myinfo;
                //获取前台提交数据
                String tbaphones=request.getParameter("tbaphones");
                String tbanames=request.getParameter("tbanames");
                String tbaaddrs=request.getParameter("tbaaddrs");
                //tb_addrs信息录入数据库
                if(db.executeUpdate("insert into tb_addrs(tb_users_id,tbaphones,tbanames,tbaaddrs) values("+user.getId()+",'"+tbaphones+"','"+tbanames+"','"+tbaaddrs+"')")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }
        //购物车添加
        if("tb_users_tb_cars".equals(tbname)){
            Object myinfo=request.getSession().getAttribute("myinfo");
            if(myinfo==null){
                json.put("msg", "请登录");
            }else{
                Tb_users user=(Tb_users)myinfo;
                //获取前台提交数据
                String tb_goods_id=request.getParameter("tb_goods_id");
                String cnums=request.getParameter("cnums");
                //tb_cars信息录入数据库
                if(db.executeUpdate("insert into tb_cars(tb_users_id,tb_goods_id,cnums) values("+user.getId()+","+tb_goods_id+","+cnums+")")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }
        //订单信息添加
        if("tb_users_tb_orders".equals(tbname)){
            Object myinfo=request.getSession().getAttribute("myinfo");
            if(myinfo==null){
                json.put("msg", "请登录");
            }else{
                Tb_users user=(Tb_users)myinfo;
                String ids=request.getParameter("ids");
                String addrid=request.getParameter("addrid");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //获取前台提交数据
                String onos=sdf.format(new Date());
                //获取收货地址
                List<Tb_addrs> tbaddrslist=GetList.getlist(Tb_addrs.class, db.executeQuery("select * from tb_addrs where id="+addrid));
                Tb_addrs addrs=tbaddrslist.get(0);
                //订单编号信息唯一性验证
                if(db.checkTrue("select * from tb_orders where onos='"+onos+"'")){
                    json.put("msg", "订单编号已存在");
                }else{
                    //tb_orders信息录入数据库
                    if(db.executeUpdate("insert into tb_orders(onos,tb_users_id,tbanames,tbaphones,tbaaddrs,otimes,oflags) values('"+onos+"',"+user.getId()+",'"+addrs.getTbanames()+"','"+addrs.getTbaphones()+"','"+addrs.getTbaaddrs()+"','"+sdf2.format(new Date())+"','已付款')")>0){
                        //处理所要删除的id字符串
                        if(ids.endsWith(",")){
                            ids=ids.substring(0, ids.length()-1);
                        }
                        List<Tb_cars> carslist=GetList.getlist(Tb_cars.class, db.executeQuery("select tb_cars.id,tb_cars.tb_users_id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_cars.tb_goods_id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gflags,tb_cars.cnums from tb_cars,tb_users,tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_cars.tb_users_id=tb_users.id  and tb_cars.tb_goods_id=tb_goods.id  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id and tb_cars.id in("+ids+")"));
                        Iterator<Tb_cars> carsit=carslist.iterator();
                        while(carsit.hasNext()){
                            Tb_cars car=carsit.next();
                            db.executeUpdate("insert into tb_details(onos,tb_goods_id,gnames,gpics,gvals,dnums) values('"+onos+"',"+car.getTb_goods_id()+",'"+car.getGnames()+"','"+car.getGpics()+"',"+car.getGvals()+","+car.getCnums()+")");
                        }
                        db.executeUpdate("delete from tb_cars where id in("+ids+")");
                        json.put("msg", "操作成功");
                    }else{
                        json.put("msg", "操作失败");
                    }
                }
            }
        }
        //订单详情添加
        if("tb_users_tb_details".equals(tbname)){
            //获取前台提交数据
            String onos=request.getParameter("onos");
            String tb_goods_id=request.getParameter("tb_goods_id");
            String gnames=request.getParameter("gnames");
            String gpics=request.getParameter("gpics");
            String gvals=request.getParameter("gvals");
            String dnums=request.getParameter("dnums");
            //tb_details信息录入数据库
            if(db.executeUpdate("insert into tb_details(onos,tb_goods_id,gnames,gpics,gvals,dnums) values('"+onos+"',"+tb_goods_id+",'"+gnames+"','"+gpics+"',"+gvals+","+dnums+")")>0){
                json.put("msg", "添加成功");
            }else{
                json.put("msg", "添加失败");
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
