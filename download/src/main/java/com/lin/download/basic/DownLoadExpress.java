package com.lin.download.basic;

import com.liulishuo.filedownloader.BaseDownloadTask;

/**
 * Created by linhui on 2017/12/7.
 */
public interface DownLoadExpress {


    int AUTO_RETRY_TIMES = 1;

    BaseDownloadTask download(IBasicInfo info);

}
