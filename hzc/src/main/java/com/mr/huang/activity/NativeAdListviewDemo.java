package com.mr.huang.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYNativeAd;
import com.iflytek.voiceads.IFLYNativeListener;
import com.iflytek.voiceads.NativeADDataRef;
import com.mr.huang.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 注意：集成原生广告，展示广告的adContainer被用户可见时（至少1/3在屏幕范围内且View.isShown()==true），才能曝光成功。在Listview中集成原生广告时可参考此demo
 * 关键处理流程： (1)第一次加载出广告时尝试曝光(onAdLoaded()中)。
 * 			   (2)滑动时，处理firstVisibleItem和lastvisibleItem的曝光(onScroll()中)。
 */

@SuppressLint("NewApi")
public class NativeAdListviewDemo extends Activity implements AbsListView.OnScrollListener, IFLYNativeListener {
	private static final String TAG = "NativeAdListviewDemo";
	// 记录某position是否请求过广告，防止重复请求
	SparseBooleanArray requested = new SparseBooleanArray();
	// 利用queue记录发送的广告请求
	// loadAd()中enqueue，onADLoaded()和onAdFailed()中dequeue并处理
	private Queue<IFLYAd> iflyAds = new LinkedList<IFLYAd>();

	private static final int ITEM_NUM = 11;// item条数
	private IFLYNativeAd nativeAd;
	private ListView listview;
	// adapter的datas，IFLYAd类型 or app定义的类型
	private ArrayList<Object> datas = new ArrayList<Object>();
	private MyAdapter adaptor;

	// 开发者维护讯飞广告类
	public class IFLYAd {
		NativeADDataRef aditem;
		boolean isExposured = false;
		int position;
		View adContainer;

		public IFLYAd(int position) {
			// TODO Auto-generated constructor stub
			this.position = position;
			nativeAd.loadAd(1);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nativead_listview);
		initNativeAd();
		getData();
		adaptor = new MyAdapter(this, datas);
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnScrollListener(this);
		listview.setAdapter(adaptor);

	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMEssage(Object a) {

	}
	// onScroll中view已经可见，检查广告曝光
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int lastvisibleItem = firstVisibleItem + visibleItemCount - 1;
		Log.i(TAG, "onScroll:visiable:" + firstVisibleItem + "-" + lastvisibleItem);
		// 若firstVisibleItem和lastvisibleItem是广告位置，则检查曝光
		if (adaptor.isAdPosition(firstVisibleItem))
			checkExposure(firstVisibleItem);
		if (adaptor.isAdPosition(lastvisibleItem))
			checkExposure(lastvisibleItem);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	// 检查曝光
	public void checkExposure(int lastvisibleItem) {
		if (lastvisibleItem > datas.size() - 1 || lastvisibleItem < 0) {
			return;
		}
		if (datas.get(lastvisibleItem) instanceof IFLYAd) {
			IFLYAd curAd = (IFLYAd) datas.get(lastvisibleItem);
			if (!curAd.isExposured && curAd != null && curAd.adContainer != null) {
				curAd.isExposured = curAd.aditem.onExposured(curAd.adContainer);
			}
		}
	}

	// 监听器
	@Override
	public void onADLoaded(List<NativeADDataRef> list) {
		// TODO Auto-generated method stub
		if (list.size() > 0) {
			final IFLYAd iflyAd = iflyAds.remove();
			iflyAd.aditem = list.get(0);
			// 添加
			datas.add(iflyAd.position, iflyAd);
			// 更新
			adaptor.notifyDataSetChanged();
			// listview刷新完毕检查一次曝光
			listview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
				@Override
				public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
						int oldRight, int oldBottom) {
					// TODO Auto-generated method stub
					Log.i(TAG, "view.visiable=" + v.getVisibility());

					checkExposure(iflyAd.position);
				}
			});

			Log.i(TAG, "onADLoaded");
		} else {
			Log.i(TAG, "NOADReturn");
		}
	}

	@Override
	public void onAdFailed(AdError arg0) {
		// TODO Auto-generated method stub
		// 获取广告失败，remove请求并将已请求标记重新设为false
		requested.put(iflyAds.remove().position, false);
		Log.e(TAG, arg0.getErrorCode() + arg0.getErrorDescription());
	}

	@Override
	public void onConfirm() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
	}

	public void initNativeAd() {
		if (nativeAd == null) {
			nativeAd = new IFLYNativeAd(this, "64DD38B4E467A29BF31AB8E7013CCE6D", this);
//			nativeAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
		}
		nativeAd.setParameter(AdKeys.DEBUG_MODE, "true");
	}

	private void getData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < ITEM_NUM; i++) {
			datas.add(i + "");
		}
	}

	public Queue<IFLYAd> getIflyAds() {
		return iflyAds;
	}
}
