package com.lin.download.dowloadfile;

import java.io.File;

/**
 * Created by linhui on 2017/11/21.
 */
public class LoadBean {
    long id;
    private String url;
    private File path;
    Object tag;

    public LoadBean(String url, File path) {
        this.url = url;
        this.path = path;
    }

    public LoadBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }
}
