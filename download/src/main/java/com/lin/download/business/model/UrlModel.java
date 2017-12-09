package com.lin.download.business.model;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/9.
 */
public class UrlModel implements UrlInfo,IModel<UrlModel>{

//    public static final TAB


    @Override
    public String getDownLoadUrl() {
        return null;
    }

    @Override
    public UrlModel clone() {
        try {
            return (UrlModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getCreateTime() {
        return null;
    }
}
