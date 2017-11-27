package com.lin.app.service.binder;

import android.view.ViewGroup;

import com.lin.app.service.Factory;
import com.lin.app.service.commander.VideoEmployee;

/**
 * Created by linhui on 2017/11/24.
 */
class VideoEmployeeImp implements VideoEmployee{
    @Override
    public void play(Object Path) {
        Factory.getVideoManager().play(Path);
    }

    @Override
    public void play(int musicRaw) {
        Factory.getVideoManager().play(musicRaw);
    }

    @Override
    public void stop() {
        Factory.getVideoManager().stop();
    }

    @Override
    public void pause() {
        Factory.getVideoManager().pause();
    }

    @Override
    public void rePlay() {
        Factory.getVideoManager().rePlay();
    }

    @Override
    public void reset() {
        Factory.getVideoManager().reset();
    }
}
