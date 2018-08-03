package com.screeclibinvoke.component.view.videoactivity;

/**
 * Created by linhui on 2018/5/28.
 */
public interface LpdsPlay {


    int QINIU = 0x9813;

    int JZ = 0x9812;

    long getDuration();

    long getCurrentPosition();

    void seekTo(int ms);

    Object getEntityVideo();

    int getVideoCompany();

    boolean isPlaying();

    void setBufferingEnabled(boolean b);

    void start();

    void pause();

    void stopPlayback();
}
