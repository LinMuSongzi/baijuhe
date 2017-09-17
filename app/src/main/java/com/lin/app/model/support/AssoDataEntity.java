package com.lin.app.model.support;

import java.util.List;

/**
 * Created by Hui on 2017/9/16.
 */

public class AssoDataEntity {

    private List<IInfoImpl> list;

    private boolean isResult;

    public List<IInfoImpl> getList() {
        return list;
    }

    public void setList(List<IInfoImpl> list) {
        this.list = list;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    public AssoDataEntity(List<IInfoImpl> list) {
        this.list = list;
    }

    public AssoDataEntity(List<IInfoImpl> list, boolean isResult) {
        this.list = list;
        this.isResult = isResult;
    }
}
