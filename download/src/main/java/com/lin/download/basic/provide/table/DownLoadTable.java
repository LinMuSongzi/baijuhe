package com.lin.download.basic.provide.table;

import com.lin.download.basic.IBasicInfo;
import com.lin.download.business.model.BaseModel;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;
import y.com.sqlitesdk.framework.annotation.TBTable;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadTable extends BaseModel<DownLoadTable> implements IBasicInfo{

    @TBTable
    public static final String TB_NAME = "tb_download_info";
    private boolean isNotitfyShowDownLoadStutas = false;
    @TBPrimarykey
    private int id;
    @TBColumn
    private long create_time = System.currentTimeMillis();
    @TBColumn()
    private String object_id;
    @TBColumn()
    private String name;
    @TBColumn()
    private String pic_url;
    private String download_url;
    @TBColumn(unique = true)
    private String save_path;
    @TBColumn(notNull = true)
    private int stutas = NOT_HAD;
    @TBColumn()
    private long toTal;
    @TBColumn()
    private long current;

    @Override
    public void setDownLoadId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public String getDownLoadId() {
        return String.valueOf(id);
    }

    @Override
    public boolean isNotitfyShowDownLoadStutas() {
        return isNotitfyShowDownLoadStutas;
    }

    @Override
    public String getObjectId() {
        return object_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPicUrl() {
        return pic_url;
    }

    @Override
    public String getDownLoadUrl() {
        return download_url;
    }

    @Override
    public String getSavePath() {
        return save_path;
    }

    @Override
    public long getToTalLeng() {
        return toTal;
    }

    @Override
    public long getCurrentLeng() {
        return current;
    }

    @Override
    public int getStatus() {
        return stutas;
    }

    public void setStutas(int stutas) {
        this.stutas = stutas;
    }

    public void setToTal(long toTal) {
        this.toTal = toTal;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public void setNotitfyShowDownLoadStutas(boolean notitfyShowDownLoadStutas) {
        isNotitfyShowDownLoadStutas = notitfyShowDownLoadStutas;
    }

    public void setCreateTime(long create_time) {
        this.create_time = create_time;
    }

    public void setObjectId(String object_id) {
        this.object_id = object_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicUrl(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setDownloadUrl(String download_url) {
        this.download_url = download_url;
    }

    public void setSavePath(String save_path) {
        this.save_path = save_path;
    }
}
