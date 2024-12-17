//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wm.utils;

import java.sql.ResultSet;
import jakarta.servlet.http.HttpSession;

public class HandlePage {
    public HandlePage() {
    }

    public static ResultSet getContent(DbConn db, int pagenum, int rownums, HttpSession session, String cksql, String serverType) {
        ResultSet rs = null;
        int pagenums = 0;
        int allnums = 0;
        Object sql = session.getAttribute(cksql);
        if (sql != null && !"".equals(sql)) {
            Object allnumsi = db.getOnlyOne("select count(*) from (" + sql + ") tmp");
            if (allnumsi == null) {
                session.setAttribute("pagenum", 0);
                session.setAttribute("pagenums", 0);
                session.setAttribute("allnums", 0);
                return null;
            } else {
                allnums = Integer.parseInt(allnumsi.toString());
                if (allnums == 0) {
                    session.setAttribute("pagenum", 0);
                    session.setAttribute("pagenums", 0);
                    session.setAttribute("allnums", 0);
                    return null;
                } else {
                    pagenums = allnums / rownums;
                    int ys = allnums % rownums;
                    if (ys != 0) {
                        ++pagenums;
                    }

                    if (pagenum > pagenums) {
                        pagenum = pagenums;
                    }

                    if (pagenum < 1) {
                        pagenum = 1;
                    }

                    if ("mysql".equals(serverType)) {
                        sql = "select * from (" + sql + ") tmp limit " + (pagenum - 1) * rownums + "," + rownums;
                    }

                    if ("sqlserver".equals(serverType)) {
                        sql = "select top " + rownums + " * from(select row_number() over(order by wm_tmprow_wm) as wm_rownumber_wm,* from (select *,'a' as wm_tmprow_wm from (" + sql + ") tmpppp) tmppp) tmpp where wm_rownumber_wm>" + (pagenum - 1) * rownums;
                    }

                    rs = db.executeQuery(sql.toString());
                    session.setAttribute("rownums", rownums);
                    session.setAttribute("pagenum", pagenum);
                    session.setAttribute("pagenums", pagenums);
                    session.setAttribute("allnums", allnums);
                    return rs;
                }
            }
        } else {
            session.setAttribute("pagenum", 0);
            session.setAttribute("pagenums", 0);
            session.setAttribute("allnums", 0);
            return null;
        }
    }
}
