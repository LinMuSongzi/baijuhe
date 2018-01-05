package com.lin.download;

import com.lin.download.business.model.DownLoadInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import y.com.sqlitesdk.framework.annotation.TBColumn;

/**
 * Created by linhui on 2017/12/25.
 */
public class MyInfo extends DownLoadInfo {


    @TBColumn
    private String adId;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    /**
     * 1495
     * 8867
     *
     * @param args
     */

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1957, 10 - 1, 2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(calendar.getTime()));

        System.out.println((System.currentTimeMillis() - calendar.getTimeInMillis()) / 1000 / 60 / 60 / 24);


        System.out.println(5000/366f);

    }


}
