package com.fileengine.commander;

import com.fileengine.commander.entity.IFileEntity;

/**
 * Created by linhui on 2017/9/28.
 */
public class DefaultFile implements IFileEntity {

    public String name;
    public String SimpleName;
    public String filePath;
    public long lenght;
    public String postfix;
    public int currentGenre;

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
