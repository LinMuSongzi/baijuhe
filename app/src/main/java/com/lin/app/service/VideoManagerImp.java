package com.lin.app.service;

import com.lin.app.service.binder.PostmanWrapper;
import com.lin.app.service.commander.MusicManager;
import com.lin.app.service.commander.VideoManager;

/**
 * Created by linhui on 2017/11/27.
 */
final class VideoManagerImp implements VideoManager<String> {

    private static VideoManagerImp musicManagerImp;
    static {
        musicManagerImp = new VideoManagerImp();
    }

    public static VideoManager getVideoManagerImp() {
        return musicManagerImp;
    }

    private VideoManagerImp() {
    }


    @Override
    public void play(String Path) {

    }

    @Override
    public void play(int musicRaw) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void rePlay() {

    }

    @Override
    public void reset() {

    }
}
