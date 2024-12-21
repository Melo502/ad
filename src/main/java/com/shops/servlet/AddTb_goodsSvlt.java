package com.shops.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.wm.utils.DbConn;
import com.shops.utils.Dbhelper;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


public class AddTb_goodsSvlt extends HttpServlet {

    public AddTb_goodsSvlt() {
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
        // 文件上传之后的文件保存路径
        String filepath = getServletContext().getRealPath("")+java.io.File.separator+"upfiles";
        File saveDir = new File(filepath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        filepath+=java.io.File.separator;
        //定义文件名和文件类型变量
        String filename = "";
        String type="";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8");
        //可接收文件的大小
        sfu.setFileSizeMax(10240*10240*20);
        sfu.setSizeMax(10240*10240*21);
        //前台数据链表
        List<FileItem> itemList;
        //定义文件变量
        File file=null;
        //定义服务器文件名变量
        String fffname=String.valueOf(System.currentTimeMillis());
        //定义前台数据变量
        String gnames="";
        String gvals="";
        String tb_sgtypes_id="";
        String gmarks="";
        String gflags="";
        try {
            itemList = sfu.parseRequest(request);
            for (FileItem fileItem : itemList) {
                if(fileItem.isFormField()){
                    String value = fileItem.getString();
                    value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    if("gnames".equals(fileItem.getFieldName())){
                        gnames=value;
                    }
                    if("gvals".equals(fileItem.getFieldName())){
                        gvals=value;
                    }
                    if("tb_sgtypes_id".equals(fileItem.getFieldName())){
                        tb_sgtypes_id=value;
                    }
                    if("gmarks".equals(fileItem.getFieldName())){
                        gmarks=value;
                    }
                    if("gflags".equals(fileItem.getFieldName())){
                        gflags=value;
                    }
                }else{
                    String name = fileItem.getName();
                    int p=name.lastIndexOf(".");
                    type=name.substring(p);
                    filename=fffname+type;
                    file = new File(filepath,filename);
                    fileItem.write(file);
                    fileItem.getOutputStream().flush();
                    fileItem.getOutputStream().close();
                }
            }
        } catch (FileUploadException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        if(file!=null){
            //创建数据库操作对象
            DbConn db=Dbhelper.getDb();
            if(db.checkTrue("select * from tb_goods where gnames='"+gnames+"'")){
                json.put("msg", "商品名称已存在");
            }else{
                String gpics=filename;
                if(db.executeUpdate("insert into tb_goods(gnames,gpics,gvals,tb_sgtypes_id,gmarks,gflags) values('"+gnames+"','"+gpics+"',"+gvals+","+tb_sgtypes_id+",'"+gmarks+"','"+gflags+"')")>0){
                    json.put("msg", "添加成功");
                }else{
                    json.put("msg", "添加失败");
                }
            }
        }else{
            json.put("msg", "文件获取失败.");
        }
        PrintWriter out=response.getWriter();
        out.print(json.toString());
        out.close();
    }

    public void init() throws ServletException {

    }
}
