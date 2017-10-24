package com.fileengine.commander.entity;

/**
 * Created by linhui on 2017/9/27.
 *
 *
 *
 */
public interface IFileEntity {

    int SIMPLE_FILE_GENRE = 100;
    int VIDEO_GENRE = 109;
    int MUSIC_GENRE = 108;
    int PHOTO_GRNRE = 102;
    int FOLDER_GRNRE = 200;

    String getName();

    String getSimpleName();

    String getFilePath();

    long getLenght();

    String getPostfix();

    int getCurrentGenre();

}
