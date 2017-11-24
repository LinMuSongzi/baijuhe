package com.lin.app.service.binder;

import android.app.Service;

import com.lin.app.service.PostmanService;
import com.lin.app.service.commander.ServiceBoss;

/**
 * Created by linhui on 2017/11/24.
 */
public final class PostmanWrapper implements ServerSupport<PostmanService>{
    private static ServiceBoss  PostmanWrapper;
    private ServerSupport mServer = new PostmanImp();
    static {
        PostmanWrapper = new PostmanWrapper();
    }

    @Override
    public Service getService() {
        return mServer.getService();
    }


    @Override
    public synchronized void attach(PostmanService server) {
        mServer.attach(server);
    }
}
