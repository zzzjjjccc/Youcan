package com.jichang.jichangprojectlibrary.util;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Do convert Something
 */

public class ConvertUtils {

    /**
     * From object to Integer
     *
     * @param value        Object
     * @param defaultValue The default value, which type is Integer
     * @return Result
     */
    public final static int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }
}
