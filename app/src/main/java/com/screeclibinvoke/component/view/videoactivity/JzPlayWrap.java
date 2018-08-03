package com.screeclibinvoke.component.view.videoactivity;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by linhui on 2018/5/28.
 */
public class JzPlayWrap implements LpdsPlay {

    JZVideoPlayerStandard standard;

    public JzPlayWrap(JZVideoPlayerStandard standard) {
        this.standard = standard;
    }

    @Override
    public long getDuration() {
        return standard.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return standard.getCurrentPositionWhenPlaying();
    }

    @Override
    public void seekTo(int ms) {
        JZMediaManager.seekTo(ms);
    }

    @Override
    public Object getEntityVideo() {
        return standard;
    }

    @Override
    public int getVideoCompany() {
        return LpdsPlay.JZ;
    }

    @Override
    public boolean isPlaying() {
        return standard.isCurrentPlay();
    }

    @Override
    public void setBufferingEnabled(boolean b) {

    }

    @Override
    public void start() {
        standard.startVideo();
    }

    @Override
    public void pause() {
        standard.release();
    }

    @Override
    public void stopPlayback() {
        JZVideoPlayer.releaseAllVideos();
    }
}
