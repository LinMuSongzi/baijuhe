package com.lin.app.model;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.app.data.model.TestBean;
import com.yeyuanyuan.web.BaseRequetResult;
import com.yeyuanyuan.web.Completed;
import com.yeyuanyuan.web.RequestResult;
import com.yeyuanyuan.web.RequetParameter;
import com.yeyuanyuan.web.StrEntity;
import com.yeyuanyuan.web.Zygote;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Hui on 2017/9/16.
 */

public class SelectInfoModel extends Model<SelectInfoModel> {

    @Bind(R.id.id_ExpandableListView)
    ExpandableListView expandableListView;

    final List<TestBean> testBeanList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_navigatiom;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(StrEntity s){
        Toast.makeText(getActivity(),s.getStrHrml(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        buildData();

        Zygote.init(getActivity().getApplicationContext());
//        Zygote.asyncExecute(Zygote.createGet(StrEntity.class, "http://www.baidu.com", null));

        Zygote.asyncExecute(Zygote.createGet(StrEntity.class,
                "http://apps.ifeimo.com/Lpds227/UserProfile/personalInformat" +
                        "ion?android_sdk=24&current_version=2.3.7.0&manufac" +
                        "turer=HUAWEI&member_id=1715698&" +
                        "target=a_lpds&user_id=1715698&version_code=20180524",null));

        expandableListView.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return testBeanList.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return testBeanList.get(groupPosition).getText().size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return testBeanList.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return testBeanList.get(groupPosition).getText().get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                GroupHolder g;
                if (convertView == null) {
                    g = new GroupHolder();
                    convertView = new TextView(getActivity());
                    convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    g.title = (TextView) convertView;
                    convertView.setTag(g);
                } else {
                    g = (GroupHolder) convertView.getTag();
                }
                g.title.setText(testBeanList.get(groupPosition).getTitle());
                return convertView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                GroupHolder g;
                if (convertView == null) {
                    g = new GroupHolder();
                    TextView t = new TextView(getActivity());
                    t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    t.setTextColor(Color.BLUE);
                    convertView = t;
                    g.title = t;
                    convertView.setTag(g);
                } else {
                    g = (GroupHolder) convertView.getTag();
                }
                g.title.setText(testBeanList.get(groupPosition).getText().get(childPosition));
                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getActivity().finish();
                        //Toast.makeText(getActivity(),testBeanList.get(groupPosition).getText().get(childPosition),Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });

    }

    private void buildData() {


        TestBean testBean = null;
        testBean = new TestBean();
        testBean.setTitle("广东");
        testBean.getText().add("广州");
        testBean.getText().add("东莞");
        testBean.getText().add("深圳");
        testBean.getText().add("珠海");
        testBean.getText().add("惠州");
        testBean.getText().add("中山");
        testBean.getText().add("清远");
        testBean.getText().add("茂名");
        testBean.getText().add("韶关");
        testBean.getText().add("肇庆");
        testBean.getText().add("汕头");
        testBean.getText().add("江门");
        testBean.getText().add("湛江");
        testBeanList.add(testBean);
        testBean = new TestBean();
        testBean.setTitle("江苏");
        testBean.getText().add("扬州");
        testBean.getText().add("徐州");
        testBean.getText().add("南京");
        testBean.getText().add("镇江");
        testBean.getText().add("连云港");
        testBeanList.add(testBean);
        testBean = new TestBean();
        testBean.setTitle("广西");
        testBean.getText().add("南宁");
        testBean.getText().add("贵港");
        testBean.getText().add("桂林");
        testBean.getText().add("白色");
        testBean.getText().add("玉林");
        testBean.getText().add("北海");
        testBean.getText().add("防城港");
        testBeanList.add(testBean);
        testBean = new TestBean();
        testBean.setTitle("江西");
        testBean.getText().add("南昌");
        testBean.getText().add("赣州");
        testBean.getText().add("鹰潭");
        testBean.getText().add("景德镇");
        testBean.getText().add("九江");
        testBeanList.add(testBean);
    }

    private static class GroupHolder {
        TextView title;
    }

    private static class ChildHolder extends GroupHolder {
    }

    @Override
    public SelectInfoModel getAffirmObject() {
        return null;
    }


}
