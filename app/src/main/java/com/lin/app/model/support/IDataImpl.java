package com.lin.app.model.support;

/**
 * Created by Hui on 2017/9/16.
 *
 * 数据加载模型
 *
 */

public interface IDataImpl {
    void loadData();
    void onDestroy();
    int getContentView();
    void finish();
}
