package com.lin.app.model;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.alllib.data.respone.CityRespone;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.request.ApiImp;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

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
        ApiImp.getAllCity(new Subscriber<CityRespone>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: 请求完毕");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(final CityRespone cityRespone) {
                Observable.from(cityRespone.getResult()).
                        flatMap(new Func1<CityRespone.ResultBean, Observable<CityRespone.ResultBean.CityBean>>() {
                            @Override
                            public Observable<CityRespone.ResultBean.CityBean> call(CityRespone.ResultBean resultBean) {
                                return Observable.from(resultBean.getCity());
                            }
                        }).
                        flatMap(new Func1<CityRespone.ResultBean.CityBean, Observable<CityRespone.ResultBean.CityBean.DistrictBean>>() {
                            @Override
                            public Observable<CityRespone.ResultBean.CityBean.DistrictBean> call(CityRespone.ResultBean.CityBean cityBean) {
                                return Observable.from(cityBean.getDistrict());
                            }
                        }).
                        map(new Func1<CityRespone.ResultBean.CityBean.DistrictBean, String>() {
                            @Override
                            public String call(CityRespone.ResultBean.CityBean.DistrictBean districtBean) {
                                return districtBean.getDistrict();
                            }
                        }).
                        subscribe(new Action1<String>() {
                            @Override
                            public void call(final String district) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        id_content_tv.setText(id_content_tv.getText() + " " + district + "  ");
                                    }
                                });

                            }
                        });
            }
        });
    }
}
