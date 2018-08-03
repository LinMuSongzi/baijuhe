package com.lin.app.model;

import android.media.JetPlayer;
import android.os.Bundle;

import com.lin.alllib.Model;
import com.lin.app.R;

import butterknife.Bind;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by linhui on 2018/5/28.
 */
public class VideoDemoModel extends Model<VideoDemoModel> {

    @Bind({R.id.videoplayer, R.id.videoplayer2})
    JZVideoPlayerStandard[] jzVideoPlayerStandard;

    @Override
    protected int getContentView() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        jzVideoPlayerStandard[0].setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266" +
                "/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        jzVideoPlayerStandard[1].setUp("http://theft.17sysj.com/fb1bac6fe922Lb?v=1.1&sign=4250e54ffad549cb50543ac52f4277a5&t=5b0bb2d2"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");

    }

    @Override
    protected void onResume() {
        super.onResume();
        jzVideoPlayerStandard[0].startVideo();
        jzVideoPlayerStandard[1].startVideo();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public VideoDemoModel getAffirmObject() {
        return null;
    }
}
