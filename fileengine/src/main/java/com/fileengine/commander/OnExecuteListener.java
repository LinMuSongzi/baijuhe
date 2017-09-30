package com.fileengine.commander;

import com.fileengine.commander.entity.IFileEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by linhui on 2017/9/27.
 */
public interface OnExecuteListener<T> {

    void onStart();

    void onNext(T collection,int count);

}
