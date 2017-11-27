package com.lin.app.service.commander;

/**
 * Created by linhui on 2017/11/24.
 */
public interface MusicEmployee extends ServiceEmployee {


    void startMusic(String filepath);
    void startMusic(int filepath);
    void pause();
    void stop();

}
