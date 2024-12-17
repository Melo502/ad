//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wm.utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class HandleExcel {
    public HandleExcel() {
    }

    public boolean createfile(String fpath, String fname, ResultSet rs) {
        boolean flg = false;
        if (rs == null) {
            return flg;
        } else {
            int rows = 0;

            try {
                rs.last();
                rows = rs.getRow();
                if (rows < 1) {
                    return flg;
                }

                rs.first();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return flg;
            }

            try {
                String path = fpath + File.separator + fname + ".xls";
                File file = new File(path);
                file.createNewFile();
                WritableWorkbook book = Workbook.createWorkbook(file);
                WritableSheet sheet = book.createSheet("Sheet_1", 0);
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();

                for(int j = 0; j < cols; ++j) {
                    Label lb = new Label(j, 0, rsmd.getColumnLabel(j + 1));
                    sheet.addCell(lb);
                }

                for(int i = 0; i < rows; ++i) {
                    for(int j = 0; j < cols; ++j) {
                        Label lb = new Label(j, i + 1, rs.getString(j + 1));
                        sheet.addCell(lb);
                    }

                    rs.next();
                }

                book.write();
                book.close();
                flg = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return flg;
        }
    }
}
