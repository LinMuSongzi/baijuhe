package com.lin.app.service.binder;

import android.app.Service;
import android.os.Binder;

import com.lin.app.service.PostmanService;
import com.lin.app.service.commander.ServiceBoss;
import com.lin.app.service.commander.ServiceEmployee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by linhui on 2017/8/31.
 */
class PostmanImp extends BaseSupport<PostmanService>{




    private PostmanService service;
    private final Map<String,ServiceEmployee> employeeMap =  new HashMap<>();
    @Override
    public void attach(PostmanService server) {
        this.service = server;
    }

    private void recruitMusicEmloyee(){
        if(!employeeMap.containsKey(MUSIC_EMPLOYEE)){
            employeeMap.put(MUSIC_EMPLOYEE,new MusicEmployeeImp());
        }
    }

    private void recruitVideoEmloyee(){
        if(!employeeMap.containsKey(VIDEO_EMPLOYEE)){
            employeeMap.put(VIDEO_EMPLOYEE,new VideoEmployeeImp());
        }
    }


}
