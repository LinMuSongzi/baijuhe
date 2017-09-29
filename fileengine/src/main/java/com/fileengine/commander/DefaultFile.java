package com.fileengine.commander;

import com.fileengine.commander.entity.IFileEntity;

/**
 * Created by linhui on 2017/9/28.
 */
class DefaultFile implements IFileEntity {

    String name;
    String SimpleName;
    String filePath;
    long lenght;
    String postfix;
    int currentGenre;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return SimpleName;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public long getLenght() {
        return lenght;
    }

    @Override
    public String getPostfix() {
        return postfix;
    }

    @Override
    public int getCurrentGenre() {
        return currentGenre;
    }
}
