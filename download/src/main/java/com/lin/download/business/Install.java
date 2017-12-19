package com.lin.download.business;

import com.lin.download.business.callback.InstallListener;

/**
 * Created by linhui on 2017/12/19.
 */
public interface Install extends InstallListener {



    void addInstallListener(InstallListener installListener);

    void removeInstallListener(InstallListener installListener);

}
