package com.lin.app.service.binder;

import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.PowerManager;

import com.lin.app.R;
import com.lin.app.service.commander.MusicEmployee;

import java.io.File;

/**
 * Created by linhui on 2017/11/24.
 */
class MusicEmployeeImp implements MusicEmployee{


    @Override
    public void startMusic(final String filepath) {
        File file = new File(filepath);

        if(file.exists() && file.isFile()){

            SoundPool soundPool = new SoundPool(10,AudioManager.STREAM_SYSTEM,0);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(1,1f,1f,0,0,1);
                }
            });
            soundPool.load(PostmanWrapper.getInstance().getService(),R.raw.alan_walker_legends_ever_die,1);
        }




    }

    @Override
    public void startMusic(int filepath) {

        if(filepath > 0){

//            SoundPool soundPool = new SoundPool(10,AudioManager.STREAM_SYSTEM,0);
//            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//                @Override
//                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//
//                }
//            });
//            int id = soundPool.load()
//            soundPool.play(id,1.0f,1.0f,1,0,1.0f);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
