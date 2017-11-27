package com.lin.app.service.commander;

import java.util.List;

/**
 * Created by linhui on 2017/11/27.
 */
public interface Play<T> {

    void play(T path);

    @Deprecated
    void play(int musicRaw);

    void stop();

    void pause();

    void rePlay();

    void reset();

}
