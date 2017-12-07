package com.lin.download.basic;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadImp implements DownLoadExpress{



    public void download(IBasicInfo info){
        final int i  = FileDownloader.getImpl().
                create(info.getDownLoadUrl()).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {

            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        }).setPath(info.getSavePath()).setSyncCallback(true).setAutoRetryTimes(AUTO_RETRY_TIMES).start();
        info.setDownLoadId(i+"");
    }


}
