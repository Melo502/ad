package com.shops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.shops.been.Tb_fgtypes;
import com.shops.been.Tb_goods;
import com.shops.been.Tb_sgtypes;
import com.shops.utils.Dbhelper;
import com.wm.utils.DbConn;
import com.wm.utils.GetList;

public class SySvlt extends HttpServlet {

    public SySvlt() {
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        HttpSession session=request.getSession();
        //获取操作标识
        String tbname=request.getParameter("tbname");
        //获取数据库操作对象
        DbConn db=Dbhelper.getDb();
        //根据操作标识，处理
        if("sy".equals(tbname)){
            List<Tb_fgtypes> fgtlist=GetList.getlist(Tb_fgtypes.class, db.executeQuery("select * from tb_fgtypes"));
            List<Tb_fgtypes> syfgtlist=new ArrayList();
            Iterator<Tb_fgtypes> fgtit=fgtlist.iterator();
            while(fgtit.hasNext()){
                Tb_fgtypes tbfg=fgtit.next();
                List<Tb_sgtypes> tbsglist=GetList.getlist(Tb_sgtypes.class, db.executeQuery("select * from tb_sgtypes where tb_fgtypes_id="+tbfg.getId()));
                tbfg.setSgtlist(tbsglist);
                syfgtlist.add(tbfg);
            }
            session.setAttribute("syfgtlist", syfgtlist);
            session.setAttribute("syflag", 1);
            //推荐商品
            List<Tb_goods> sytjglist=GetList.getlist(Tb_goods.class, db.executeQuery("select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id and gflags='是' "));
            //新商品
            List<Tb_goods> syxglist=GetList.getlist(Tb_goods.class, db.executeQuery("select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id order by tb_goods.id desc limit 0,12 "));
            session.setAttribute("sytjglist", sytjglist);
            request.setAttribute("alist", syxglist);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        if("getglist".equals(tbname)){
            String sgtid=request.getParameter("sgtid");
            String gname=request.getParameter("gname");
            String sql="select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id";
            if(sgtid!=null&&sgtid!=""){
                sql+=" and tb_sgtypes.id="+sgtid;
            }
            if(gname!=null&&gname!=""){
                sql+=" and tb_goods.gnames like '%"+gname+"%'";
            }
            List<Tb_goods> glist=GetList.getlist(Tb_goods.class, db.executeQuery(sql));
            request.setAttribute("alist", glist);
            request.getRequestDispatcher("/index.jsp").forward(request, response);;
            return;
        }
        if("goods".equals(tbname)){
            String id=request.getParameter("id");
            List<Tb_goods> glist=GetList.getlist(Tb_goods.class, db.executeQuery("select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id and tb_goods.id="+id));
            Tb_goods tbg=glist.get(0);
            request.setAttribute("tbg", tbg);
            request.getRequestDispatcher("/goods.jsp").forward(request, response);
            return;
        }
    }


    public void init() throws ServletException {

    }

}
