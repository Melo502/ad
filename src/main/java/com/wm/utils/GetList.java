//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wm.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GetList {
    public GetList() {
    }

    public static List getlist(Class c, ResultSet rs) {
        List list = new ArrayList();
        if (rs != null) {
            try {
                Field[] flds = c.getDeclaredFields();

                while(rs.next()) {
                    Object obj = c.newInstance();

                    for(Field f : flds) {
                        f.setAccessible(true);
                        if (checkColumn(f.getName(), rs)) {
                            if (!Integer.TYPE.equals(f.getType()) && !Integer.class.equals(f.getType())) {
                                if (!Float.TYPE.equals(f.getType()) && !Float.class.equals(f.getType())) {
                                    if (!Double.TYPE.equals(f.getType()) && !Double.class.equals(f.getType())) {
                                        if (!Boolean.TYPE.equals(f.getType()) && !Boolean.class.equals(f.getType())) {
                                            if (Timestamp.class.equals(f.getType())) {
                                                f.set(obj, rs.getTimestamp(f.getName()));
                                            } else {
                                                f.set(obj, rs.getString(f.getName()));
                                            }
                                        } else {
                                            f.set(obj, rs.getBoolean(f.getName()));
                                        }
                                    } else {
                                        f.set(obj, rs.getDouble(f.getName()));
                                    }
                                } else {
                                    f.set(obj, rs.getFloat(f.getName()));
                                }
                            } else {
                                f.set(obj, rs.getInt(f.getName()));
                            }
                        }
                    }

                    list.add(obj);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static boolean checkColumn(String column, ResultSet rs) {
        try {
            if (rs.findColumn(column) > 0) {
                return true;
            }
        } catch (SQLException var3) {
        }

        return false;
    }
}
