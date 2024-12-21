package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.shops.been.Tb_admins;
import com.shops.been.Tb_users;
import com.wm.utils.DbConn;
import com.shops.utils.Dbhelper;

import net.sf.json.JSONObject;


public class UpdSvlt extends HttpServlet {

    public UpdSvlt() {
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
        //管理员信息修改功能实现
        if("tb_admins_tb_admins".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String anos=request.getParameter("anos");
            String apwds=request.getParameter("apwds");
            String anames=request.getParameter("anames");
            String aphones=request.getParameter("aphones");
            //修改对账号进行唯一性验证
            if(db.checkTrue("select * from tb_admins where anos='"+anos+"' and id!="+id)){
                json.put("msg", "账号已存在");
            }else{
                //修改功能实现
                if(db.executeUpdate("update tb_admins set anos='"+anos+"',apwds='"+apwds+"',anames='"+anames+"',aphones='"+aphones+"' where id="+id)>0){
                    json.put("msg", "修改成功");
                }else{
                    json.put("msg", "修改失败");
                }
            }
        }
        //商品主类修改功能实现
        if("tb_admins_tb_fgtypes".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String fgtname=request.getParameter("fgtname");
            //修改对类型名称进行唯一性验证
            if(db.checkTrue("select * from tb_fgtypes where fgtname='"+fgtname+"' and id!="+id)){
                json.put("msg", "类型名称已存在");
            }else{
                //修改功能实现
                if(db.executeUpdate("update tb_fgtypes set fgtname='"+fgtname+"' where id="+id)>0){
                    json.put("msg", "修改成功");
                }else{
                    json.put("msg", "修改失败");
                }
            }
        }
        //商品分类修改功能实现
        if("tb_admins_tb_sgtypes".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String sgtnames=request.getParameter("sgtnames");
            String tb_fgtypes_id=request.getParameter("tb_fgtypes_id");
            //修改对分类名称进行唯一性验证
            if(db.checkTrue("select * from tb_sgtypes where sgtnames='"+sgtnames+"' and id!="+id)){
                json.put("msg", "分类名称已存在");
            }else{
                //修改功能实现
                if(db.executeUpdate("update tb_sgtypes set sgtnames='"+sgtnames+"',tb_fgtypes_id="+tb_fgtypes_id+" where id="+id)>0){
                    json.put("msg", "修改成功");
                }else{
                    json.put("msg", "修改失败");
                }
            }
        }
        //订单信息修改功能实现
        if("tb_admins_tb_orders".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String oflag=request.getParameter("oflag");
            //修改功能实现
            if(db.executeUpdate("update tb_orders set oflags='"+oflag+"' where id="+id)>0){
                json.put("msg", "操作成功");
            }else{
                json.put("msg", "操作失败");
            }
        }
        //用户信息修改
        if("tb_users_tb_users".equals(tbname)){
            Object myinfo=request.getSession().getAttribute("myinfo");
            if(myinfo==null){
                json.put("msg", "请重新登录");
            }else{
                Tb_users user=(Tb_users)myinfo;
                //获取前台提交数据
                String upwds=request.getParameter("upwds");
                String uphones=request.getParameter("uphones");
                String unames=request.getParameter("unames");
                String uaddrs=request.getParameter("uaddrs");
                if(db.checkTrue("select id from tb_users where uphones='"+uphones+"' and id!="+user.getId())){
                    json.put("msg", "电话已存在");
                }else{
                    //tb_users信息录入数据库
                    if(db.executeUpdate("update tb_users set uphones='"+uphones+"',unames='"+unames+"',uaddrs='"+uaddrs+"',upwds='"+upwds+"' where id="+user.getId())>0){
                        json.put("msg", "保存成功");
                    }else{
                        json.put("msg", "保存失败");
                    }
                }
            }
        }
        //收货地址修改功能实现
        if("tb_users_tb_addrs".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String tbaphones=request.getParameter("tbaphones");
            String tbanames=request.getParameter("tbanames");
            String tbaaddrs=request.getParameter("tbaaddrs");
            //修改功能实现
            if(db.executeUpdate("update tb_addrs set tbaphones='"+tbaphones+"',tbanames='"+tbanames+"',tbaaddrs='"+tbaaddrs+"' where id="+id)>0){
                json.put("msg", "修改成功");
            }else{
                json.put("msg", "修改失败");
            }
        }
        //购物车修改功能实现
        if("tb_users_tb_cars".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String cnums=request.getParameter("cnums");
            //修改功能实现
            if(db.executeUpdate("update tb_cars set cnums="+cnums+" where id="+id)>0){
                json.put("msg", "修改成功");
            }else{
                json.put("msg", "修改失败");
            }
        }
        //订单信息修改功能实现
        if("tb_users_tb_orders".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String onos=request.getParameter("onos");
            String tb_users_id=request.getParameter("tb_users_id");
            String tbanames=request.getParameter("tbanames");
            String tbaphones=request.getParameter("tbaphones");
            String tbaaddrs=request.getParameter("tbaaddrs");
            String otimes=request.getParameter("otimes");
            String oflags=request.getParameter("oflags");
            //修改对订单编号进行唯一性验证
            if(db.checkTrue("select * from tb_orders where onos='"+onos+"' and id!="+id)){
                json.put("msg", "订单编号已存在");
            }else{
                //修改功能实现
                if(db.executeUpdate("update tb_orders set onos='"+onos+"',tb_users_id="+tb_users_id+",tbanames='"+tbanames+"',tbaphones='"+tbaphones+"',tbaaddrs='"+tbaaddrs+"',otimes='"+otimes+"',oflags='"+oflags+"' where id="+id)>0){
                    json.put("msg", "修改成功");
                }else{
                    json.put("msg", "修改失败");
                }
            }
        }
        //订单详情修改功能实现
        if("tb_users_tb_details".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String onos=request.getParameter("onos");
            String tb_goods_id=request.getParameter("tb_goods_id");
            String gnames=request.getParameter("gnames");
            String gpics=request.getParameter("gpics");
            String gvals=request.getParameter("gvals");
            String dnums=request.getParameter("dnums");
            //修改功能实现
            if(db.executeUpdate("update tb_details set onos='"+onos+"',tb_goods_id="+tb_goods_id+",gnames='"+gnames+"',gpics='"+gpics+"',gvals="+gvals+",dnums="+dnums+" where id="+id)>0){
                json.put("msg", "修改成功");
            }else{
                json.put("msg", "修改失败");
            }
        }
        //密码修改功能实现
        if("updpwd".equals(tbname)){
            //获取用户登录信息
            Object myinfo=request.getSession().getAttribute("myinfo");
            Object utype=request.getSession().getAttribute("utype");
            if(myinfo==null||utype==null){
                json.put("msg", "请重新登录");
            }else{
                //获取页面提交的修改数据
                String opwd=request.getParameter("opwd");
                String npwd=request.getParameter("npwd");
                if("tb_admins".equals(utype.toString())){
                    Tb_admins ad=(Tb_admins)myinfo;
                    if(db.checkTrue("select id from tb_admins where apwds='"+opwd+"' and id="+ad.getId())){
                        json.put("msg", "旧密码输入有误");
                    }else{
                        //修改功能实现
                        if(db.executeUpdate("update tb_admins set apwds='"+npwd+"' where id="+ad.getId())>0){
                            json.put("msg", "修改成功");
                        }else{
                            json.put("msg", "修改失败");
                        }
                    }
                }
                if("tb_users".equals(utype.toString())){
                    Tb_users ad=(Tb_users)myinfo;
                    if(db.checkTrue("select id from tb_users where upwds='"+opwd+"' and id="+ad.getId())){
                        json.put("msg", "旧密码输入有误");
                    }else{
                        //修改功能实现
                        if(db.executeUpdate("update tb_users set upwds='"+npwd+"' where id="+ad.getId())>0){
                            json.put("msg", "修改成功");
                        }else{
                            json.put("msg", "修改失败");
                        }
                    }
                }
            }
        }
        if("tb_admins_tb_users".equals(tbname)){
            //获取页面提交的修改数据
            String id=request.getParameter("id");
            String isLogin=request.getParameter("isLogin");
            //修改功能实现
            if(db.executeUpdate("update tb_users set isLogin='"+isLogin+"' where id="+id)>0){
                json.put("msg", "修改成功");
            }else{
                json.put("msg", "修改失败");
            }
        }
        PrintWriter out=response.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }
    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }
}
