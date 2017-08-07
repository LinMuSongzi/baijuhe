package com.lin.app.request.retrofit;

import com.lin.app.data.entity.UserEntity;
import com.lin.app.data.respone.UserOprateRespone;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lpds on 2017/7/27.
 */
public interface AnXingApi {

    String PATH = "https://linhui.lizuolin.com";

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST("/MrLin.svc/register1")
    Observable<UserOprateRespone> registerUser(@Body UserEntity requestBody);

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST("/MrLin.svc/login1")
    Observable<UserOprateRespone> loginByUser(@Body UserEntity requestBody);

}
