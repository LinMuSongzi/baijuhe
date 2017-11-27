package com.lin.app.service.binder;

import com.lin.app.service.Factory;
import com.lin.app.service.commander.MusicEmployee;

import java.util.List;

/**
 * Created by linhui on 2017/11/24.
 */
class MusicEmployeeImp implements MusicEmployee {


    @Override
    public void play(List musics, String tag) {
        Factory.getMusicManager().play(musics,tag);
    }

    @Override
    public void next() {
        Factory.getMusicManager().next();
    }

    @Override
    public void clearMusics(String tag) {
        Factory.getMusicManager().clearMusics(tag);
    }

    @Override
    public void playModel(int mode) {
        Factory.getMusicManager().playModel(mode);
    }

    @Override
    public void play(Object path) {
        Factory.getMusicManager().play(path);
    }

    @Override
    public void play(int musicRaw) {
        Factory.getMusicManager().play(musicRaw);
    }

    @Override
    public void stop() {
        Factory.getMusicManager().stop();
    }

    @Override
    public void pause() {
        Factory.getMusicManager().pause();
    }

    @Override
    public void rePlay() {
        Factory.getMusicManager().rePlay();
    }

    @Override
    public void reset() {
        Factory.getMusicManager().reset();
    }
}
