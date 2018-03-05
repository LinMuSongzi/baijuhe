package com.mr.huang.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYNativeAd;
import com.iflytek.voiceads.IFLYNativeListener;
import com.iflytek.voiceads.NativeADDataRef;
import com.mr.huang.R;
import com.mr.huang.common.ExposureEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhui on 2018/1/12.
 */
public class XunfeiTest extends AppCompatActivity {


    private static final String TAG = "XunfeiTest";
    private ListView id_listview;
    IFLYNativeAd iflyNativeAd;
    NativeADDataRef nativeADDataRef;
    private final List<AdBean> datas = new ArrayList<>();
    private MyAdapter adaptor;

    private final List<ExposureEntity> exposureEntityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xufnei);
        adaptor = new MyAdapter();
        id_listview = (ListView) findViewById(R.id.id_listview);

        id_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastvisibleItem = firstVisibleItem + visibleItemCount - 1;
                Log.i(TAG, "onScroll:visiable:" + firstVisibleItem + "-" + lastvisibleItem);
                // 若firstVisibleItem和lastvisibleItem是广告位置，则检查曝光
                if (adaptor.isAdPosition(firstVisibleItem))
                    checkExposure(firstVisibleItem);
                if (adaptor.isAdPosition(lastvisibleItem))
                    checkExposure(lastvisibleItem);
            }
        });
        id_listview.setAdapter(adaptor);

//        loaderData();


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE}, 100);

    }
    // 检查曝光
    public void checkExposure(int position) {
        if (position > datas.size() - 1 || position < 0) {
            return;
        }
        if (datas.get(position) instanceof AdBean && datas.get(position).pic != null) {
            AdBean curAd = datas.get(position);
            if (!curAd.isExposured && curAd != null && curAd.adContainer != null) {
                curAd.isExposured = nativeADDataRef.onExposured(curAd.adContainer);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {

            loaderData();

        }


    }

    private void loaderData() {

        IFLYNativeListener iflyNativeListener = new IFLYNativeListener() {
            @Override
            public void onADLoaded(List<NativeADDataRef> list) {
                Log.i(TAG, "onADLoaded: ");
                if (list != null && list.size() > 0) {
                    succeed(list.get(0));
                }
            }

            @Override
            public void onAdFailed(AdError adError) {
                Log.i(TAG, "onAdFailed: adError code = " + adError.getErrorCode() + "  messgae" + adError.getErrorDescription());
                errorAd();
            }

            @Override
            public void onConfirm() {
                Log.i(TAG, "onConfirm: 确认");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onConfirm: 取消");
            }
        };
        iflyNativeAd = new IFLYNativeAd(this, "A09B234AB5ED0AF2298B95CC6F1B49A4", iflyNativeListener);
        iflyNativeAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
        iflyNativeAd.loadAd(1);
    }

    private void succeed(NativeADDataRef nativeADDataRef) {
        this.nativeADDataRef = nativeADDataRef;
        datas.clear();
        for (int i = 0; i < 10; i++) {

            datas.add(new AdBean());

        }
        AdBean adBean = new AdBean();
        adBean.pic = nativeADDataRef.getImage();
        adBean.text = nativeADDataRef.getSubTitle();
        adBean.title = nativeADDataRef.getTitle();
        datas.add(2, adBean);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((BaseAdapter) id_listview.getAdapter()).notifyDataSetChanged();
                id_listview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                               int oldRight, int oldBottom) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "view.visiable=" + v.getVisibility());

                        checkExposure(2);
                    }
                });
            }
        });
    }

    private void errorAd() {


    }

    private static class AdBean {

        private String title;
        private String pic;
        private String text;
        private boolean isChoose = false;
        public boolean isExposured = false;
        public View adContainer;

        public NativeADDataRef aditem;
    }

    private static class Holder {

        private ImageView id_iv;
        private TextView id_title;
        private TextView id_text;


    }


    private class MyAdapter extends BaseAdapter {


        @Override
        public int getItemViewType(int position) {
            if (datas.get(position).pic != null) {
                return 1;
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public AdBean getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final AdBean adBean = getItem(position);
            if (getItemViewType(position) == 1) {
                convertView = getLayoutInflater().inflate(R.layout.item_ad_text2, null, false);
                adBean.adContainer = convertView;
//                if(!adBean.isExposured){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            nativeADDataRef.onExposured(adBean.adContainer);
//                            adBean.isExposured = true;
//                        }
//                    });
//                }
                Glide.with(XunfeiTest.this).load(adBean.pic).into((ImageView) convertView.findViewById(R.id.id_iv));
//                convertView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                if (iflyNativeAd != null) {
//                                    Log.i(TAG, "onTouch: XunFeiAdvertisementImp ACTION_DOWN");
//                                    iflyNativeAd.setParameter(AdKeys.CLICK_POS_DY, v.getY() + "");
//                                    iflyNativeAd.setParameter(AdKeys.CLICK_POS_DX, v.getX() + "");
//                                }
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                break;
//                            case MotionEvent.ACTION_UP:
//                                if (iflyNativeAd != null) {
//                                    Log.i(TAG, "onTouch: XunFeiAdvertisementImp ACTION_UP");
//                                    iflyNativeAd.setParameter(AdKeys.CLICK_POS_UY, v.getY() + "");
//                                    iflyNativeAd.setParameter(AdKeys.CLICK_POS_UX, v.getX() + "");
//                                }
//                                break;
//                        }
//                        return false;
//                    }
//                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nativeADDataRef.onClicked(v);
                    }
                });
//                nativeADDataRef.onExposured(convertView);
            } else {
                Holder holder = null;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_ad_text, null, false);
                    holder = new Holder();
                    holder.id_iv = (ImageView) convertView.findViewById(R.id.id_iv);
                    holder.id_text = (TextView) convertView.findViewById(R.id.id_text);
                    holder.id_title = (TextView) convertView.findViewById(R.id.id_title);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }


                if (adBean.pic != null) {

                } else {
                    holder.id_iv.setImageResource(R.drawable.ic_arrow_back_white_24dp);
                    convertView.setOnTouchListener(null);
                }

                holder.id_title.setText(adBean.title);
                holder.id_text.setText(adBean.text);
//                holder.id_title.setText(adBean.title);
            }
            return convertView;
        }

        public boolean isAdPosition(int firstVisibleItem) {
            return firstVisibleItem == 2;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMEssage(Object a) {

    }
}
