package com.lin.app.request.retrofit;

import com.lin.app.data.respone.InitEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by linhui on 2017/12/6.
 */
public interface LpdsApi {

    String PATH = "http://apps.ifeimo.com/lpds229/";



    @GET("LpdsInitialize/AdvertisementController?")
    Observable<InitEntity> requstinit(@QueryMap Map<String,String> map);

}
