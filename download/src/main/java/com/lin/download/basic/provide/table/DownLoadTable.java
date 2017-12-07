package com.lin.download.basic.provide.table;

import com.lin.download.basic.IBasicInfo;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadTable implements IBasicInfo ,IModel<DownLoadTable>{

    public static final String TB_NAME = "tb_download_info";
    private boolean isNotitfyShowDownLoadStutas = false;
    @TBPrimarykey
    private long id;
    @TBColumn
    private String create_time;
    private String object_id;
    private String name;
    private String pic_url;
    @TBColumn(unique = true)
    private String download_url;
    @TBColumn(unique = true)
    private String save_path;
    @TBColumn(unique = true)
    private int stutas = NOT_HAD;
    @TBColumn(unique = true)
    private String toTal;
    @TBColumn(unique = true)
    private String current;

    @Override
    public DownLoadTable clone() {
        try {
            return (DownLoadTable) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTableName() {
        return TB_NAME;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getCreateTime() {
        return create_time;
    }

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
    public String getToTalLeng() {
        return toTal;
    }

    @Override
    public String getCurrentLeng() {
        return current;
    }

    @Override
    public int getStatus() {
        return stutas;
    }

    public void setStutas(int stutas) {
        this.stutas = stutas;
    }

    public void setToTal(String toTal) {
        this.toTal = toTal;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setNotitfyShowDownLoadStutas(boolean notitfyShowDownLoadStutas) {
        isNotitfyShowDownLoadStutas = notitfyShowDownLoadStutas;
    }

    public void setCreateTime(String create_time) {
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
