package com.lin.app.service.commander;

import android.os.Message;

/**
 * Created by linhui on 2017/11/27.
 */
public interface Business {

    int SUM = 0x985471;
    int STOP = 0x985475;
    int START_MUSIC = 0x985473;

    boolean handlerMsg(Message message);

}
