package com.lin.app.data.bean;

/**
 * Created by lpds on 2017/6/21.
 */
public class UserBean {

    static String name;
    static String user;

    public static void setName(String name) {
        UserBean.name = name;
    }

    public static void setUser(String user) {
        UserBean.user = user;
    }

    public static String getName() {
        return name;
    }

    public static String getUser() {
        return user;
    }
}
