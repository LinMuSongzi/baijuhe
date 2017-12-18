package com.lin.download.business;

import com.lin.download.business.callback.FileDownloadExceptionListener;
import com.lin.download.business.callback.OperatorRespone;

import java.util.Set;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Operator {

    void addFileDownloadException(FileDownloadExceptionListener fileDownloadOutOfSpaceExceptionListener);

    void removeFileDownloadException(Object fileDownloadOutOfSpaceExceptionListener);

    void addOperatorRespone(OperatorRespone operatorRespone);

    void removeOperatorRespone(OperatorRespone operatorRespone);

    Set<OperatorRespone> getOperatorRespones();

    Set<FileDownloadExceptionListener> getFileDownloadExceptionListeners();

    void handlerFileDownloadException(Throwable throwable);


}
