package com.yeyuanyuan.web;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
final class Access implements IAccessNetwork<RequestResult> {




    static IAccessNetwork create() {
        return new Access();
    }

    private OkHttpClient baseOkHttpClient;
    private Executor executor;

    private Access() {
        init();
    }

    @Override
    public void asyncExecute(final RequetEntity<RequestResult> requetEntity) {
        baseOkHttpClient.newCall(requetEntity.getRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    Zygote.getInterceptorMnager().onFailure(call, e, requetEntity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    Zygote.getInterceptorMnager().onResponse(call, response, requetEntity);
            }
        });

    }

    @Override
    public void execute(RequetEntity<RequestResult> requetEntity) {
        try {
            Response response = baseOkHttpClient.newCall(requetEntity.getRequest()).execute();
            requetEntity.setResult(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        this.baseOkHttpClient = new okhttp3.OkHttpClient.Builder().
//                addInterceptor(Zygote.getInterceptorMnager()).
//                addNetworkInterceptor(Zygote.getInterceptorMnager()).
        cache(new Cache(Zygote.context.getCacheDir(), CACHE_LONG_SIZE)).
                        connectTimeout(TIME_OUT, TimeUnit.SECONDS).
                        build();
    }

}
