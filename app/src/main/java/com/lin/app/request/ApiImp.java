package com.lin.app.request;

import android.util.Log;

import com.lin.alllib.data.respone.CityRespone;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.common.SpManager;
import com.lin.app.common.SubscriberImp;
import com.lin.app.request.retrofit.WeatherApi;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * Created by linhui on 2017/8/8.
 */
public class ApiImp {

    public static void getAllCity() {
//        CityRespone cityRespone = SpManager.getCitys();
//        if(cityRespone == null) {
            RequestManager.getInstance()
                    .config(RequestManager.getInstance().
                                    getApi(WeatherApi.class).
                                    getAllMsg(WeatherApi.KEY),
                            new SubscriberImp<CityRespone>() {
                                @Override
                                public void onNext2(CityRespone cityRespone) {
//                                    SpManager.saveCitys(cityRespone);
//                                    EventBus.getDefault().post(cityRespone);
                                    Log.i("CityRespone", "onNext2: "+cityRespone);

                                }
                            });
//        }else{
//            EventBus.getDefault().post(cityRespone);
//        }
    }

}
