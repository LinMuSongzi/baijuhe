package com.lin.download.business.model;

import com.lin.download.basic.provide.table.DownLoadTable;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;
import y.com.sqlitesdk.framework.annotation.TBTable;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/9.
 */
public class UrlTable extends BaseModel<UrlInfo> implements UrlInfo{

    @TBTable
    public static final String TB_NAME = "tb_download_url";


    @TBColumn
    private String download_url;


    @Override
    public String getDownLoadUrl() {
        return download_url;
    }

    public void  setDownLoadUrl(String s){
        download_url = s;
    }

}
