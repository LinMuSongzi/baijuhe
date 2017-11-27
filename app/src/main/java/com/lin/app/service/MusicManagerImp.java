package com.lin.app.service;

import com.lin.app.service.commander.MusicManager;

import java.util.List;

/**
 * Created by linhui on 2017/11/27.
 */
final class MusicManagerImp implements MusicManager<String> {
    private static MusicManagerImp musicManagerImp;
    static {
        musicManagerImp = new MusicManagerImp();
    }

    public static MusicManager getMusicManagerImp() {
        return musicManagerImp;
    }

    private MusicManagerImp() {
    }

    @Override
    public void play(String musicPath) {

    }

    @Override
    public void play(int musicRaw) {

    }

    @Override
    public void play(List<String> musics, String tag) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void next() {

    }

    @Override
    public void rePlay() {

    }

    @Override
    public void clearMusics(String tag) {

    }

    @Override
    public void playModel(int mode) {

        switch (mode){
            case MusicManager.DEFAULT_MODE:
                break;
            case MusicManager.ONE_MODE:
                break;
            case MusicManager.REPETITION_MODE:
                break;
            case MusicManager.REPETITION_ONE_MODE:
                break;
        }

    }

    @Override
    public void reset() {

    }


}
