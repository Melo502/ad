//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wm.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class GetListByExcel {
    public GetListByExcel() {
    }

    public static List getAllByExcel(Class c, String file) {
        List list = new ArrayList();

        try {
            Workbook rwb = Workbook.getWorkbook(new File(file));
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();
            int rows = rs.getRows();
            Field[] flds = c.getDeclaredFields();

            for(int i = 1; i < rows; ++i) {
                Object obj = c.newInstance();
                int j = 0;

                for(Field f : flds) {
                    f.setAccessible(true);
                    if (!Integer.TYPE.equals(f.getType()) && !Integer.class.equals(f.getType())) {
                        if (!Float.TYPE.equals(f.getType()) && !Float.class.equals(f.getType())) {
                            if (!Double.TYPE.equals(f.getType()) && !Double.class.equals(f.getType())) {
                                if (!Boolean.TYPE.equals(f.getType()) && !Boolean.class.equals(f.getType())) {
                                    f.set(obj, rs.getCell(j++, i).getContents());
                                } else {
                                    f.set(obj, Boolean.parseBoolean(rs.getCell(j++, i).getContents()));
                                }
                            } else {
                                f.set(obj, Double.parseDouble(rs.getCell(j++, i).getContents()));
                            }
                        } else {
                            f.set(obj, Float.parseFloat(rs.getCell(j++, i).getContents()));
                        }
                    } else {
                        f.set(obj, Integer.parseInt(rs.getCell(j++, i).getContents()));
                    }
                }

                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
