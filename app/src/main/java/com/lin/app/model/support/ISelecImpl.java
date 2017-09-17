package com.lin.app.model.support;

import android.app.Activity;

import java.util.List;

/**
 * Created by Hui on 2017/9/16.
 */

public interface ISelecImpl extends IDataImpl,IActivityImpl{



    void init();

    void refresh(IInfoImpl iInfo);

}
