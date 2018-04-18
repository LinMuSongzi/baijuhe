package com.feimomideaencode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.os.Handler;
import android.view.ViewStructure;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by linhui on 2018/3/30.
 */
public class SupprotEngine {

    private MediaRecorder mRecorder;
    private IConfig iConfig;
    private AtomicBoolean isRecording = new AtomicBoolean(false);

    public void start(IConfig config) {
        prepare(config);
    }

    private void prepare(IConfig config) {
        this.iConfig = config;
        if (mRecorder == null) {
            mRecorder = new MediaRecorder(); // 创建MediaRecorder
            try {
                // 设置音频采集方式
                mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                //设置视频的采集方式
                mRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                //设置文件的输出格式
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//aac_adif， aac_adts， output_format_rtp_avp， output_format_mpeg2ts ，webm
                //设置audio的编码格式
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //设置video的编码格式
                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                //设置录制的视频编码比特率
                mRecorder.setVideoEncodingBitRate(iConfig.getBitRate());
                //设置录制的视频帧率,注意文档的说明:
                mRecorder.setVideoFrameRate(iConfig.getFps());
                //设置要捕获的视频的宽度和高度
                mRecorder.setVideoSize(iConfig.getWidth(), iConfig.getHeight());//最高只能设置640x480
                //设置记录会话的最大持续时间（毫秒）
//            mRecorder.setMaxDuration(60 * 1000);
                mRecorder.setPreviewDisplay(iConfig.getVirtualDisplay().getSurface());
                String path = iConfig.getPath();
                //设置输出文件的路径
                mRecorder.setOutputFile(path);
                //准备录制
                mRecorder.prepare();
                //开始录制
                mRecorder.start();
                isRecording.set(true);
            } catch (Exception e) {
                isRecording.set(false);
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public void pause() {
        mRecorder.pause();
    }

    public void reStart() {
        mRecorder.start();
    }

    public void end() {
        mRecorder.stop();
    }

    public void release() {
        iConfig = null;
    }


}
