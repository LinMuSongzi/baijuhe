package com.lin.app.service.commander;

import java.util.List;

/**
 * Created by linhui on 2017/11/27.
 */
public interface MusicManager<T> extends VideoManager<T>{

    int ONE_MODE = 0x88;
    int REPETITION_MODE = 0x89;
    int REPETITION_ONE_MODE = 0x90;
    int DEFAULT_MODE = 0x100;

    void play(List<T> musics,String tag);

    void next();

    void clearMusics(String tag);

    void playModel(int mode);

}
