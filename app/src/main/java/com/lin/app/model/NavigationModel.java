package com.lin.app.model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lin.alllib.Model;
import com.lin.alllib.framwork.manager.ActivityManager;
import com.lin.app.NavigationActivity;
import com.lin.app.R;
import com.lin.app.data.respone.WeatherRespone;
import com.lin.app.request.RequestManager;

import java.util.Collection;
import java.util.Iterator;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/7/26.
 */
public class NavigationModel extends Model {

    @Bind(R.id.id_content_tv)
    TextView id_content_tv;

    @Override
    protected int getContentView() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id_content_tv.setText("hello");
        RequestManager.getInstance().getAll(new Subscriber<WeatherRespone>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: 请求完毕");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(final WeatherRespone weatherRespone) {
                Observable.
                        from(weatherRespone.getResult()).
                        flatMap(new Func1<WeatherRespone.ResultBean, Observable<WeatherRespone.ResultBean.CityBean>>() {
                            @Override
                            public Observable<WeatherRespone.ResultBean.CityBean> call(WeatherRespone.ResultBean resultBean) {
                                return Observable.from(resultBean.getCity());
                            }
                        }).
                        flatMap(new Func1<WeatherRespone.ResultBean.CityBean, Observable<WeatherRespone.ResultBean.CityBean.DistrictBean>>() {
                            @Override
                            public Observable<WeatherRespone.ResultBean.CityBean.DistrictBean> call(WeatherRespone.ResultBean.CityBean cityBean) {
                                return Observable.from(cityBean.getDistrict());
                            }
                        }).
                        map(new Func1<WeatherRespone.ResultBean.CityBean.DistrictBean, String>() {
                            @Override
                            public String call(WeatherRespone.ResultBean.CityBean.DistrictBean districtBean) {
                                return districtBean.getDistrict();
                            }
                        }).
                        subscribe(new Action1<String>() {
                            @Override
                            public void call(String district) {
                                id_content_tv.setText(id_content_tv.getText() + " " + district + "  ");
                            }
                        });
            }
        });
    }
}
