package com.lin.download;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
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

import com.lin.download.basic.Entrance;
import com.lin.download.basic.IBasicInfo;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.BusinessWrap;
import com.lin.download.business.ViewSupportLoader;
import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.basic.OperatorRespone;
import com.lin.download.util.DownloadUtil;
import com.lin.download.util.RecyclerViewCursorAdapter;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.util.MD5Util;

public class FileListActivity extends AppCompatActivity {
    private final int CODE = 0x9131;
    private RecyclerView id_RecyclerView;
    private MyAdapter adapter;
    private OperatorRespone operatorRespone = new OperatorRespone<List<DownLoadInfo>>() {
        @Override
        public int getCode() {
            return CODE;
        }

        @Override
        public void success(List<DownLoadInfo> object) {

            if (!isFinishing()) {

                for (DownLoadInfo downLoadTable : object) {

                    Log.i("success", "success: " + downLoadTable);

                }

                if (object.size() == 0) {
                    Log.i("success", "success: no data");
                }
            }

        }

        @Override
        public void error() {

        }
    };
    private ViewSupportLoader loader = new ViewSupportLoader();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        FileDownloader.setup(this);
        createDefualtGame();
        init();
        loader.init(this, 100, adapter);
        Entrance.addOperatorRespone(operatorRespone);
        Entrance.findStutasDownloadList(CODE, IBasicInfo.PAUSE_STATUS);
        show();
    }

    private void show() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(Html.fromHtml("是开启自动下载？"))
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      Entrance.notifyStatus();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    protected void onDestroy() {
        super.onDestroy();
        Entrance.removeOperatorRespone(operatorRespone);
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
    }

    private void download(int id) {
        Entrance.download(id);
    }

    private void download(DownLoadInfo id) {
        Entrance.download(id);
    }

    private void deleteItem(final int id, String name) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(Html.fromHtml("是否删除 <font color=#a09f51>\"" + name + "\"</font> 下载？"))
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Entrance.delete(id, false);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("确定(删除源文件)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Entrance.delete(id, true);

            }
        }).show();
    }

    public void createDefualtGame() {
        List<DownLoadInfo> loadEntities = new ArrayList<>();
        if (getSharedPreferences("aaa", MODE_PRIVATE).getInt("key", 1) == 100) {
            return;
        }
        getSharedPreferences("aaa", MODE_PRIVATE).edit().putInt("key", 100).apply();
        DownLoadInfo downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[0]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "王者荣耀.apk");
        downLoadTable.setName("王者荣耀");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[0]);
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[1]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "火影忍者.apk");
        downLoadTable.setName("火影忍者");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[1]);
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[2]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "天龙八部.apk");
        downLoadTable.setName("天龙八部");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[2]);
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[3]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "微信.apk");
        downLoadTable.setName("微信");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[3]);
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[4]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "QQ.apk");
        downLoadTable.setName("QQ");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[4]);
        loadEntities.add(downLoadTable);

        downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(DownloadUtil.GAME_LIST[5]);
        downLoadTable.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "lin-download" + File.separator + "开心消消乐.apk");
        downLoadTable.setName("开心消消乐");
        downLoadTable.setObjectId(DownloadUtil.GAME_LIST[5]);
        loadEntities.add(downLoadTable);

        for (final DownLoadInfo d : loadEntities) {
            Entrance.addTask(d);
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

    private class MyAdapter extends RecyclerViewCursorAdapter<MyViewHodler> {

        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public void onBindViewHolder(MyViewHodler holder, Cursor cursor) {
            DownLoadInfo downLoadTable = null;

            try {
                downLoadTable = BusinessUtil.reflectCursorOne(cursor, DownLoadInfo.class, false);
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

            holder.id_status_path.setTextColor(Color.GRAY);
            switch (downLoadTable.getStatus()) {
                case DownLoadInfo.COMPLETED_STATUS:
                    holder.id_status_path.setText("完成");
                    holder.id_status_path.setTextColor(Color.RED);
                    holder.id_progressBar.setProgress(100);
                    break;
                case DownLoadInfo.ERROR_STATUS:
                    holder.id_status_path.setText("下载错误");
                    break;
                case DownLoadInfo.PAUSE_STATUS:
                    holder.id_status_path.setText("已暂停");
                    break;
                case DownLoadInfo.DOING_STATUS:
                    holder.id_status_path.setText("正在下载 "+((int) ((downLoadTable.getCurrentLeng() * 1f / downLoadTable.getToTalLeng()) * 100))+" %");

//                    holder.id_status_path.setEnabled(false);
                    break;
                case DownLoadInfo.NOT_HAD_STATUS:
                    holder.id_status_path.setText("可以下载");
                    break;
                case DownLoadInfo.WAITTING_STATUS:
                    holder.id_status_path.setText("等待中");
                    holder.id_status_path.setTextColor(Color.BLUE);
                    break;
            }

            final DownLoadInfo finalDownLoadTable = downLoadTable;
            holder.id_status_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    v.setEnabled(false);
                    switch (stutas) {
                        case DownLoadInfo.COMPLETED_STATUS:
                            Entrance.launchApp(v.getContext(), savePath);
                            break;
                        case DownLoadInfo.ERROR_STATUS:
                            download(finalDownLoadTable);
                            break;
                        case DownLoadInfo.PAUSE_STATUS:
                            download(finalDownLoadTable);
                            break;
                        case DownLoadInfo.DOING_STATUS:
                            Entrance.pause(id);
                            break;
                        case DownLoadInfo.NOT_HAD_STATUS:
                            download(finalDownLoadTable);
                            break;
                        case DownLoadInfo.WAITTING_STATUS:
                            download(finalDownLoadTable);
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
}
