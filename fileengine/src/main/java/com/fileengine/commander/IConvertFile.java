package com.fileengine.commander;

import com.fileengine.commander.entity.IFileEntity;

import java.io.File;

/**
 * Created by linhui on 2017/9/28.
 */
public interface IConvertFile<T extends IFileEntity> {

    T getEntity(File file);

}
