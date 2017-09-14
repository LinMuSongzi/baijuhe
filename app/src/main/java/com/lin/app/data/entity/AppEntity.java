package com.lin.app.data.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by linhui on 2017/9/14.
 */
public class AppEntity extends BaseEntity<AppEntity>{


    private String appname;

    private String PackageName;

    private String versionCode;

    private Drawable Icon;

    private long firstInstallTime;

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public Drawable getIcon() {
        return Icon;
    }

    public void setIcon(Drawable drawable) {
        this.Icon = drawable;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
