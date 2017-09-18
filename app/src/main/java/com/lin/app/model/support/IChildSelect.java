package com.lin.app.model.support;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Hui on 2017/9/16.
 *
 * fragment 实现
 *
 */

public interface IChildSelect extends IDataImpl{

    View getFragment();

    String getTitle();

}
