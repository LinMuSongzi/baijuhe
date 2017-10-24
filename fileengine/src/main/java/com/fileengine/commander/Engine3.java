package com.fileengine.commander;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.fileengine.commander.entity.DocBean;
import com.fileengine.commander.entity.EngineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhui on 2017/9/30.
 */
public class Engine3 {




    /**
     * 查询SD卡里可以上传的文档
     */
    public void queryFiles(final OnExecuteListener<DocBean> onExecuteListener, EngineEntity engineEntity){
        onExecuteListener.onStart();
        List<DocBean> list = new ArrayList<DocBean>(){
            @Override
            public boolean add(DocBean o) {
                onExecuteListener.onNext(o,size());
                return super.add(o);
            }
        };

        String[] projection = new String[] { MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        FileAppclication.appclication.getContentResolver().notifyChange(MediaStore.Files.getContentUri("external"),null);

        Cursor cursor = FileAppclication.appclication.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                projection,
                MediaStore.Files.FileColumns.MIME_TYPE+" = ?",
                new String[]{"video/mp4"},
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                DocBean docBean = new DocBean();
                int idindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    String id = cursor.getString(idindex);
                    String path = cursor.getString(dataindex);
                    String size = cursor.getString(sizeindex);
                    docBean.setId(id);
                    docBean.setPath(path);
                    docBean.setSize(size);
                    int dot=path.lastIndexOf("/");
                    String name=path.substring(dot+1);
                    list.add(docBean);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }
}
