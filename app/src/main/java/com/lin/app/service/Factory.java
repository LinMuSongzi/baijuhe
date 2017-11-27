package com.lin.app.service;

import com.lin.app.service.commander.MusicManager;
import com.lin.app.service.commander.VideoManager;

/**
 * Created by linhui on 2017/11/27.
 */
public abstract class Factory {

    private Factory() {
    }

    public static MusicManager getMusicManager() {
        return MusicManagerImp.getMusicManagerImp();
    }

    public static VideoManager getVideoManager() {
        return VideoManagerImp.getVideoManagerImp();
    }

}
