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
public class DelSvlt extends HttpServlet {

    public DelSvlt() {
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
        //获取信息id集合
        String ids=request.getParameter("ids");
        int i=0;
        json.put("msg", 0);
        //处理所要删除的id字符串
        if(ids.endsWith(",")){
            ids=ids.substring(0, ids.length()-1);
        }
        //管理员信息数据删除
        if("tb_admins".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_admins where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //客户信息数据删除
        if("tb_users".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_users where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //收货地址数据删除
        if("tb_addrs".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_addrs where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //商品主类数据删除
        if("tb_fgtypes".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_fgtypes where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //商品分类数据删除
        if("tb_sgtypes".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_sgtypes where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //商品信息数据删除
        if("tb_goods".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_goods where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //购物车数据删除
        if("tb_cars".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_cars where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //订单信息数据删除
        if("tb_orders".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_orders where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
            }
        }
        //订单详情数据删除
        if("tb_details".equals(tbname)){
            if(ids.length()>0){
                //删除所要删除是信息语句
                String sql="delete from tb_details where id in("+ids+")";
                //删除操作
                i=db.executeUpdate(sql);
            }
            //如果i>0 说明删除掉了想要删除的数据
            if(i>0){
                json.put("msg", "删除成功");
            }else{
                json.put("msg", "删除失败");
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
