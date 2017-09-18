package com.lin.app.model.support.popupwindow;

import com.lin.app.model.support.IDataImpl;

/**
 * Created by Hui on 2017/9/17.
 */

public interface IPopup extends IDataImpl{

    void show();

    void dismiss();

    void setPopupListener(PopupListener popupListener);

    interface PopupListener{

        void show();

        void dismiss();

    }

}
