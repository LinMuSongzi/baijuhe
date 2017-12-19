package com.lin.download.business.callback;

import com.lin.download.business.IHierarchy;

/**
 * Created by linhui on 2017/12/18.
 */
public interface FileDownloadExceptionListener extends IHierarchy{


    void onException(Throwable ex);

}
