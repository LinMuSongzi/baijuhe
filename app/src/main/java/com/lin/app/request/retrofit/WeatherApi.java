package com.lin.app.request.retrofit;

import com.google.gson.JsonObject;
import com.lin.alllib.data.respone.CityRespone;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lpds on 2017/5/31.
 */
public interface WeatherApi {

    String KEY = "1e47c1e7361fe";
    String PATH = "http://apicloud.mob.com/v1/weather/";

    @GET("query?")
    Observable<JsonObject> getByCity(@Query("key") String key, @Query("city") String city, @Query("province") String province);


    @GET("citys?")
    Observable<CityRespone> getAllMsg(@Query("key") String key);

}
