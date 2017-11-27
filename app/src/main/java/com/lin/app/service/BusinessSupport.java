package com.lin.app.service;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;

import com.lin.app.service.binder.PostmanWrapper;
import com.lin.app.service.commander.Business;

/**
 * Created by linhui on 2017/11/27.
 */
class BusinessSupport implements Business{


    @Override
    public boolean handlerMsg(Message msg) {
        switch (msg.what) {
            case SUM:
                Message message = Message.obtain();
                message.what = SUM;
                message.arg1 = msg.arg1;
                message.arg2 = msg.arg2;
                Bundle bundle = new Bundle();
                bundle.putInt("sum", msg.arg1 + msg.arg2);
                message.obj = bundle;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case STOP:
                return false;
            case START_MUSIC:
                PostmanWrapper.getInstance().getMusicEmployee().startMusic(msg.getData().getString("path"));
                break;

        }
        return true;
    }


}
