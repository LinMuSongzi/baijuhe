package com.lin.app.service.binder;

import android.app.Service;

import com.lin.app.service.PostmanService;
import com.lin.app.service.commander.MusicEmployee;
import com.lin.app.service.commander.ServiceBoss;
import com.lin.app.service.commander.ServiceEmployee;
import com.lin.app.service.commander.VideoEmployee;

/**
 * Created by linhui on 2017/11/24.
 */
public final class PostmanWrapper implements ServerSupport<PostmanService>{
    private static ServerSupport  PostmanWrapper;
    private ServerSupport mServer = new PostmanImp();
    static {
        PostmanWrapper = new PostmanWrapper();
    }

    private PostmanWrapper() {
    }

    public static ServerSupport getInstance(){
        return PostmanWrapper;
    }

    @Override
    public Service getService() {
        return mServer.getService();
    }


    @Override
    public synchronized void attach(PostmanService server) {
        mServer.attach(server);

    }

    @Override
    public MusicEmployee getMusicEmployee() {
        return mServer.getMusicEmployee();
    }

    @Override
    public VideoEmployee getVideoEmployee() {
        return mServer.getVideoEmployee();
    }
}
