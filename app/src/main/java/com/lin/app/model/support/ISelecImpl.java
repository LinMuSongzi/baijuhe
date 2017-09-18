package com.lin.app.model.support;

import android.app.Activity;
import android.view.View;

import java.util.List;

/**
 * Created by Hui on 2017/9/16.
 */

public interface ISelecImpl extends IDataImpl,IActivityImpl{


    /**
     * 调用此方法初始化吗，此类必须用于activity主页面
     */
    @Deprecated
    void init();

    void init(View view);

    void refresh(IInfoImpl iInfo);

}
