package com.lin.app.service.binder;

import android.app.Service;
import android.os.Binder;

import com.lin.app.service.PostmanService;
import com.lin.app.service.commander.MusicEmployee;
import com.lin.app.service.commander.ServiceBoss;
import com.lin.app.service.commander.ServiceEmployee;
import com.lin.app.service.commander.VideoEmployee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by linhui on 2017/8/31.
 */
class PostmanImp extends BaseSupport<PostmanService>{
    private final Map<String,ServiceEmployee> employeeMap =  new HashMap<>();
    @Override
    public MusicEmployee getMusicEmployee() {
        return (MusicEmployee) employeeMap.get(MUSIC_EMPLOYEE);
    }

    @Override
    public VideoEmployee getVideoEmployee() {
        return (VideoEmployee) employeeMap.get(VIDEO_EMPLOYEE);
    }

    void recruitMusicEmloyee(){
        if(!employeeMap.containsKey(MUSIC_EMPLOYEE)){
            employeeMap.put(MUSIC_EMPLOYEE,new MusicEmployeeImp());
        }
    }

    void recruitVideoEmloyee(){
        if(!employeeMap.containsKey(VIDEO_EMPLOYEE)){
            employeeMap.put(VIDEO_EMPLOYEE,new VideoEmployeeImp());
        }
    }


    @Override
    protected void attachAfter() {
        recruitMusicEmloyee();
        recruitVideoEmloyee();
    }
}
