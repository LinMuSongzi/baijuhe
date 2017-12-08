package com.lin.download;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.table.DownLoadTable;
import com.lin.download.util.DownloadUtil;
import com.lin.download.util.RecyclerViewCursorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

public class FileListActivity extends AppCompatActivity {

    RecyclerView id_RecyclerView;
    private List<DownLoadTable> loadEntities = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        createDefualtGame();
        init();
    }

    private void init() {
        id_RecyclerView = (RecyclerView) findViewById(R.id.id_RecyclerView);
        id_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        id_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        id_RecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.right = 40;
                outRect.left = 40;
                outRect.top = 60;
                if (itemPosition == adapter.getItemCount() - 1) {
                    outRect.bottom = 60;
                }
            }
        });
        id_RecyclerView.setAdapter((adapter = new MyAdapter(this, null, 1)));

        Loader<Cursor> loader = getLoaderManager().initLoader(100, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(FileListActivity.this, DownLoadProvider.CONTENT_QUERY_ALL_URI
                        , null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                adapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                adapter.swapCursor(null);

            }
        });
        loader.startLoading();
    }


    private class MyAdapter extends RecyclerViewCursorAdapter<MyViewHodler> {

        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public void onBindViewHolder(MyViewHodler holder, Cursor cursor) {
            final int id = cursor.getInt(0);
            final String name = cursor.getString(cursor.getColumnIndex("name"));
            holder.id_file.setText(name);
            holder.id_download_path.setText("下载地址：" + cursor.getString(cursor.getColumnIndex("download_url")));
//            holder.id_progressBar.setText(cursor.getString(cursor.getColumnIndex("name")));
            holder.id_save_path.setText("存储地址：" + cursor.getString(cursor.getColumnIndex("save_path")));
            holder.id_size.setText("总大小：" + cursor.getInt(cursor.getColumnIndex("toTal")));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(id, name);
                }
            });
        }

        @Override
        protected void onContentChanged() {

        }

        @Override
        public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(id_RecyclerView.getContext()).inflate(R.layout.adapter_download, parent, false);
            return new MyViewHodler(v);
        }
    }

    private void deleteItem(final int id, String name) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(Html.fromHtml("是否删除 <font color=#a09f51>\"" + name + "\"</font> 下载？"))
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getContentResolver().delete(DownLoadProvider.CONTENT_DELETE_URI, "id = ?", new String[]{id + ""}) > 0) {

                                    Toast.makeText(FileListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    public void createDefualtGame() {

        if(getSharedPreferences("aaa",MODE_PRIVATE).getInt("key",1) == 100){
            return;
        }
        getSharedPreferences("aaa",MODE_PRIVATE).edit().putInt("key",100).apply();
        DownLoadTable downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[0]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "王者荣耀.apk");
        downLoadTable.setName("王者荣耀");
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[1]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "火影忍者.apk");
        downLoadTable.setName("火影忍者");
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[2]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "天龙八部.apk");
        downLoadTable.setName("天龙八部");
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[3]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "微信.apk");
        downLoadTable.setName("微信");
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[4]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "QQ.apk");
        downLoadTable.setName("QQ");
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[5]);
        downLoadTable.setSavePath(getFilesDir().getAbsolutePath() + File.separator + "开心消消乐.apk");
        downLoadTable.setName("开心消消乐");
        loadEntities.add(downLoadTable);

        for (final DownLoadTable d : loadEntities) {

            Access.run(new Execute() {
                @Override
                public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                    Business.getInstances().insert(sqLiteDatabase, d);
                    getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                }

                @Override
                public void onExternalError() {

                }
            });
        }

//        for (String url : DownloadUtil.GAME_LIST) {
//
//
//
//            final DownLoadTable downLoadTable = new DownLoadTable();
//            downLoadTable.setDownloadUrl(url);
//            downLoadTable.setSavePath(getFilesDir().getAbsolutePath()+);
//            Access.run(new Execute() {
//                @Override
//                public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
//                    Business.getInstances().insert(sqLiteDatabase, downLoadTable);
//                    getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI,null);
//                }
//
//                @Override
//                public void onExternalError() {
//
//                }
//            });
//        }


    }


    private static class MyViewHodler extends RecyclerView.ViewHolder {

        TextView id_file;
        ProgressBar id_progressBar;
        TextView id_size;
        TextView id_save_path;
        TextView id_download_path;

        public MyViewHodler(View itemView) {
            super(itemView);
            id_file = (TextView) itemView.findViewById(R.id.id_file);
            id_size = (TextView) itemView.findViewById(R.id.id_size);
            id_save_path = (TextView) itemView.findViewById(R.id.id_save_path);
            id_download_path = (TextView) itemView.findViewById(R.id.id_download_path);
            id_progressBar = (ProgressBar) itemView.findViewById(R.id.id_progressBar);
        }
    }


}
