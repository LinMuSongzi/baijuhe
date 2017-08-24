package com.lin.alllib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.alllib.data.EmptyEntity;
import com.lin.alllib.framwork.DebugGod;
import com.lin.alllib.framwork.FragmentModel;
import com.lin.alllib.framwork.commander.IDeal;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by linhui on 2017/8/10.
 */
public abstract class WoodFragment<T> extends Fragment implements IDeal<T>{

    private final String TAG = getClass().getSimpleName();
    private FragmentModel model;
    private View contentView;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DebugGod.i(TAG,"Fragment : onCreateView");
        if(contentView == null){
            contentView = LayoutInflater.from(getActivity()).inflate(model.getContentView(),null);
        }
        ButterKnife.bind(model,contentView);
        model.onCreate((WoodActivity) getActivity(),savedInstanceState);
        return contentView;
    }

    @Override
    public final void onResume() {
        DebugGod.i(TAG,"Fragment : onResume");
        super.onResume();
        model.onResume();
    }

    @Override
    public final void onStart() {
        DebugGod.i(TAG,"Fragment : onStart");
        super.onStart();
        model.onStart();
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugGod.i(TAG,"Fragment : onCreate");
        if(model == null) {
            model = getFragmentModel();
            model.setFragment(this);
        }
        EventBus.getDefault().register(model);
        model.onCreateBefore();
    }

    protected abstract FragmentModel getFragmentModel();

    @Override
    public final void onDestroy() {
        DebugGod.i(TAG,"Fragment : onDestroy");
        EventBus.getDefault().unregister(model);
        model.onDestroy();
        ButterKnife.unbind(model);
        super.onDestroy();
    }

    @Override
    public final void onStop() {
        DebugGod.i(TAG,"Fragment : onStop");
        super.onStop();
        model.onStop();
    }

    @Override
    public final void onPause() {
        DebugGod.i(TAG,"Fragment : onPause");
        super.onPause();
        model.onPause();
    }

    @Override
    public final void onDestroyView() {
        DebugGod.i(TAG,"Fragment : onDestroyView");
        super.onDestroyView();
        model.onDestroyView();
    }


    @Override
    public String getValueOfString(String key) {
        return null;
    }

    @Override
    public int getValueOfInteger(String key) {
        return 0;
    }

    @Override
    public float getValueOfFloat(String key) {
        return 0;
    }

    @Override
    public Object getValueOfObject(String key) {
        return null;
    }

    @Override
    public T getAffirmObject(String key) {
        return null;
    }
}
