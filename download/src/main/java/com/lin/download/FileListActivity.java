package com.lin.download;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.table.DownLoadTable;
import com.lin.download.business.DownLoadViewController;
import com.lin.download.util.DownloadUtil;
import com.lin.download.util.RecyclerViewCursorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.util.MD5Util;

public class FileListActivity extends AppCompatActivity {

    RecyclerView id_RecyclerView;
//    private List<DownLoadTable> loadEntities = new ArrayList<>();
    MyAdapter adapter;
    Loader<Cursor> loader;


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

        loader = getLoaderManager().initLoader(100, null, new LoaderManager.LoaderCallbacks<Cursor>() {

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
    }

    private class MyAdapter extends RecyclerViewCursorAdapter<MyViewHodler> {

        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public void onBindViewHolder(MyViewHodler holder, Cursor cursor) {
            DownLoadTable downLoadTable = null;

            try {
                downLoadTable = BusinessUtil.reflectCursorOne(cursor, DownLoadTable.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (downLoadTable == null) {
                return;
            }
            final String savePath = downLoadTable.getSavePath();
            final int stutas = downLoadTable.getStatus();
            final int id = downLoadTable.getId();
            final String name = downLoadTable.getName();

            Log.i("downLoadTable", "onBindViewHolder: downLoadTable = "+downLoadTable);


            holder.id_file.setText(downLoadTable.getName());
            holder.id_download_path.setText("下载地址：" + cursor.getString(cursor.getColumnIndex("download_url")));
            holder.id_progressBar_tv.setText("已下载：" + String.format("%.2f M", downLoadTable.getCurrentLeng() / 1024f / 1024));
            holder.id_save_path.setText("存储地址：" + cursor.getString(cursor.getColumnIndex("save_path")));
            holder.id_size.setText("总大小：" + String.format("%.2f M", downLoadTable.getToTalLeng() / 1024f / 1024));
            holder.id_progressBar.setProgress((int) ((downLoadTable.getCurrentLeng() * 1f / downLoadTable.getToTalLeng()) * 100));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(id, name);
                }
            });


            switch (downLoadTable.getStatus()) {
                case DownLoadTable.COMPLETED:
                    holder.id_status_path.setText("完成");
//                    holder.id_status_path.setEnabled(true);
                    holder.id_progressBar.setProgress(100);
                    break;
                case DownLoadTable.ERROR:
                    holder.id_status_path.setText("下载错误");
                    break;
                case DownLoadTable.PAUSE:
                    holder.id_status_path.setText("已暂停");
                    break;
                case DownLoadTable.DOING:
                    holder.id_status_path.setText("正在下载");
//                    holder.id_status_path.setEnabled(false);
                    break;
                case DownLoadTable.NOT_HAD:
                    holder.id_status_path.setText("可以下载");
                    break;
            }

            holder.id_status_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    v.setEnabled(false);
                    switch (stutas) {
                        case DownLoadTable.COMPLETED:
                            launchApp(getpaasdas(savePath),savePath);
                            break;
                        case DownLoadTable.ERROR:
                            download(id);
                            break;
                        case DownLoadTable.PAUSE:
                            download(id);
                            break;
                        case DownLoadTable.DOING:
                            DownLoadViewController.getInstance().pause(id);
                            break;
                        case DownLoadTable.NOT_HAD:
                            download(id);
                            break;
                    }
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

    private String getpaasdas(String apk){

        return getPackageManager().getPackageArchiveInfo(apk, PackageManager.GET_ACTIVITIES).packageName;
    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void launchApp(String packageName, String appPath) {
        // 启动目标应用
        if (isInstallByread(packageName)) {
            // 获取目标应用安装包的Intent
            Intent intent = getPackageManager().getLaunchIntentForPackage(
                    packageName);
            startActivity(intent);
        }
        // 安装目标应用
        else {
            Intent intent = new Intent();
            // 设置目标应用安装包路径
            intent.setDataAndType(Uri.fromFile(new File(appPath)),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }

    private void download(int id) {
        DownLoadViewController.getInstance().download(id);
    }

    private void deleteItem(final int id, String name) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(Html.fromHtml("是否删除 <font color=#a09f51>\"" + name + "\"</font> 下载？"))
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownLoadViewController.getInstance().delete(id);
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
        List<DownLoadTable> loadEntities = new ArrayList<>();
        if (getSharedPreferences("aaa", MODE_PRIVATE).getInt("key", 1) == 100) {
            return;
        }
        getSharedPreferences("aaa", MODE_PRIVATE).edit().putInt("key", 100).apply();
        DownLoadTable downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[0]);
        downLoadTable.setSavePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "王者荣耀.apk");
        downLoadTable.setName("王者荣耀");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[0]));
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[1]);
        downLoadTable.setSavePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "火影忍者.apk");
        downLoadTable.setName("火影忍者");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[1]));
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[2]);
        downLoadTable.setSavePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "天龙八部.apk");
        downLoadTable.setName("天龙八部");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[2]));
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[3]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "微信.apk");
        downLoadTable.setName("微信");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[3]));
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[4]);
        downLoadTable.setSavePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "QQ.apk");
        downLoadTable.setName("QQ");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[4]));
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadTable();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[5]);
        downLoadTable.setSavePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "开心消消乐.apk");
        downLoadTable.setName("开心消消乐");
        downLoadTable.setGameId(MD5Util.convert(DownloadUtil.GAME_LIST[5]));
        loadEntities.add(downLoadTable);

        for (final DownLoadTable d : loadEntities) {
            DownLoadViewController.getInstance().addTask(d);
        }

    }


    private static class MyViewHodler extends RecyclerView.ViewHolder {

        TextView id_file;
        ProgressBar id_progressBar;
        TextView id_size;
        TextView id_save_path;
        TextView id_download_path;
        TextView id_status_path;
        TextView id_progressBar_tv;

        public MyViewHodler(View itemView) {
            super(itemView);
            id_file = (TextView) itemView.findViewById(R.id.id_file);
            id_size = (TextView) itemView.findViewById(R.id.id_size);
            id_save_path = (TextView) itemView.findViewById(R.id.id_save_path);
            id_download_path = (TextView) itemView.findViewById(R.id.id_download_path);
            id_progressBar = (ProgressBar) itemView.findViewById(R.id.id_progressBar);
            id_status_path = (TextView) itemView.findViewById(R.id.id_status_path);
            id_progressBar_tv = (TextView) itemView.findViewById(R.id.id_progressBar_tv);
        }
    }


}
