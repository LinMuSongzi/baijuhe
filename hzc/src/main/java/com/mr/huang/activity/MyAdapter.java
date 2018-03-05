package com.mr.huang.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mr.huang.activity.NativeAdListviewDemo.*;

import com.iflytek.voiceads.NativeADDataRef;
import com.mr.huang.R;

import java.util.ArrayList;

class MyAdapter extends BaseAdapter {
	private NativeAdListviewDemo adActivity;
	private ArrayList<Object> datas;

	LayoutInflater inflater;
	private static final int ItemViewTypeCount = 2; // Listview的item类型数量
	private static final int DEFAULT_TYPE = 0; // 默认类型
	private static final int AD_TYPE = 1; // 广告类型

	public MyAdapter(NativeAdListviewDemo adActivity, ArrayList<Object> datas) {
		// TODO Auto-generated constructor stub
		this.adActivity = adActivity;
		this.datas = datas;
		inflater = (LayoutInflater) adActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return ItemViewTypeCount;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub

		// 如果是IFLYAd类型数据，view类型为AD_TYPE
		if (datas.get(position) instanceof IFLYAd) {
			return AD_TYPE;
		}
		return super.getItemViewType(position);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 根据position返回两种类型的view。当是广告展示位置时返回adContainer。
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//若是广告位置，则请求广告
		if (isAdPosition(position)) {
			loadAd(position);
		}

		int type = getItemViewType(position);
		Object data = datas.get(position);
		MyHolder holder = null;
		switch (type) {
		case DEFAULT_TYPE:
			//加载app数据
			String strData = (String) data;
			if (convertView == null) {
				holder = new MyHolder();
				convertView = inflater.inflate(R.layout.listitem, null);
				holder.tv = (TextView) convertView.findViewById(R.id.item_textview);
				holder.tv.setText(strData);
				convertView.setTag(holder);
			} else {
				holder = (MyHolder) convertView.getTag();
				holder.tv.setText((String) datas.get(position));

			}
			return convertView;
		case AD_TYPE:
			// 广告数据
			IFLYAd iflyAd = (IFLYAd) data;
			// 广告容器
			RelativeLayout adContainer = (RelativeLayout) inflater.inflate(R.layout.nativelistitem, null);
			// 保存广告容器
			iflyAd.adContainer = adContainer;
			// 加载并显示广告内容
			showAD(iflyAd.adContainer, iflyAd.aditem);
			return adContainer;
		default:
			return null;
		}
	}

	class MyHolder {
		TextView tv;
	}

	// 广告位置：0，15，30...
	public boolean isAdPosition(int position) {
		return position%10 == 0;
	}
	// 加载广告
	public void loadAd(int position) {
		if (!adActivity.requested.get(position, false)) {
			adActivity.requested.put(position, true);
			adActivity.getIflyAds().add(adActivity.new IFLYAd(position));
		}
	}
	// 展示广告
	private void showAD(View view, final NativeADDataRef adItem) {
		((TextView)view.findViewById(R.id.ad_source_mark)).setText(adItem.getAdSourceMark()+"|广告");
//		$.id(R.id.img_logo).image((String) adItem.getIcon(), false, true);
//		$.id(R.id.img_poster).image(adItem.getImage(), false, true);
		((TextView)view.findViewById(R.id.text_name)).setText((String) adItem.getTitle());
		((TextView)view.findViewById(R.id.text_desc)).setText((String) adItem.getSubTitle());
		view.findViewById(R.id.img_poster).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				adItem.onClicked(view);
			}
		});
	}
}
