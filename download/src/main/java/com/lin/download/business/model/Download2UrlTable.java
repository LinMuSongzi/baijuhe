package com.lin.download.business.model;

import com.lin.download.basic.provide.table.DownLoadTable;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBTable;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/9.
 */
public class Download2UrlTable extends BaseModel<DownLoadTable> {

    @TBTable
    public static final String TB_NAME = "tb_download2urlTable";

    @TBColumn(unique = true)
    private String download_url;

    @TBColumn(unique = true)
    private int download_id;

    public int getDownload_id() {
        return download_id;
    }

    public void setDownload_id(int download_id) {
        this.download_id = download_id;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
