package com.yeyuanyuan.web;

/**
 * Created by linhui on 2017/12/5.
 */
public class StrEntity extends BaseRequetResult {

    String strHrml;

    public String getStrHrml() {
        return strHrml;
    }

    public void setStrHrml(String strHrml) {
        this.strHrml = strHrml;
    }

    @Override
    public String toString() {
        return "StrEntity{" +
                "strHrml='" + strHrml + '\'' +
                '}';
    }
}
