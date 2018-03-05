package com.mr.huang.common;

import java.lang.reflect.Field;

/**
 * Created by linhui on 2018/1/5.
 */
public class XmlUtil {


    /**
     * 寻找此属性，递归到父类
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
//                e.printStackTrace();
            }
        }
        return null;
    }

}
