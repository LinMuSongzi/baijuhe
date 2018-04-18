package com.common;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.view.Surface;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by linhui on 2018/4/11.
 */
public class SurfaceExecute extends Thread{


    private MediaCodec videocode;
    private MediaCodec audiaCode;
    private Surface surface;
    private AtomicBoolean isStop = new AtomicBoolean(false);
    private MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

    private String path;

    private MediaMuxer mediaMuxer;
    private AtomicBoolean isStartMediaMuxer = new AtomicBoolean(false);


    public SurfaceExecute(String path, Surface surface) {
        this.path = path;
        this.surface = surface;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void running() throws IOException {
        if (videocode == null) {
            mediaMuxer = new MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, 720, 1080);
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 20 * 100 * 1000);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
//            mediaFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR);
            videocode = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            videocode.configure(mediaFormat, surface, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            videocode.start();
        }

        int i = videocode.dequeueOutputBuffer(bufferInfo, 10 * 1000);
        if (i > 0) {

            ByteBuffer buffer = videocode.getOutputBuffer(i);
            if (buffer != null) {
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    bufferInfo.size = 0;
                }
                if (bufferInfo.size != 0) {
                    buffer.position(bufferInfo.offset);
                    buffer.limit(bufferInfo.offset + bufferInfo.size);
//                bufferInfo.presentationTimeUs = (nanoTime - mediaStartTime - pts) / 1000;
                    mediaMuxer.writeSampleData(0, buffer, bufferInfo);
//                    Log.i(TAG, "video outputBuffer=" + outputBuffer);
                }
                videocode.releaseOutputBuffer(i, false);
            }

        }else if(i == -2 && !isStartMediaMuxer.get()){
            mediaMuxer.addTrack(videocode.getOutputFormat());
            mediaMuxer.start();
            isStartMediaMuxer.set(true);
        }

    }


    public  void initAudio() {
        while (!isStop.get()) {
            try {
                running();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (mediaMuxer != null) {
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaMuxer = null;
        }

        if (videocode != null) {
            videocode.stop();
            videocode.release();
            videocode = null;
        }

    }

    public void stopCodec(){
        this.isStop.set(true);
    }


    @Override
    public void run() {
        initAudio();
    }
}
