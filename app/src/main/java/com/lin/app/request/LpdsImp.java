package com.lin.app.request;

import com.lin.alllib.framwork.RequestManager;
import com.lin.app.common.SubscriberImp;
import com.lin.app.data.respone.InitEntity;
import com.lin.app.request.retrofit.LpdsApi;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by linhui on 2017/12/6.
 */
public class LpdsImp {

    public static void reuqestInit(Subscriber<InitEntity> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("channel", "lenovo");
        map.put("current_version", "2.3.0.11");
        map.put("target", "a_lpds");
        map.put("version_code", "20171204");
        RequestManager requestManager = RequestManager.getInstance();
        requestManager.config(requestManager.getApi(LpdsApi.class).requstinit(map), subscriber);
    }

}
