package com.lin.app.model.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lin.alllib.AppLife;
import com.lin.app.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hui on 2017/9/16.
 */

public class GameFragment extends Fragment implements IChildSelect {

    private View contentView;
    private final String XML_NAME = "cache_selcet";
    private final String XML_VAUE = "cache";

    private List<IInfoImpl> ALL_GAMES = new ArrayList<>();
    private List<IInfoImpl> HOT_GAMES = new ArrayList<>();
    private List<IInfoImpl> CACHE_GAMES = new ArrayList<>();

    private IInfoImpl selectInfo;

    @Bind(R.id.id_cache_layout)
    View id_cache_layout;
    @Bind(R.id.id_cache_rc)
    RecyclerView id_cache_rc;
    @Bind(R.id.id_hot_game_rc)
    RecyclerView id_hot_game_rc;

    private final int HOT_GAME_FLAG = 0x123;
    private final int CACHE_FLAG = 0x1221;
    private int flag;

    private ISelecImpl iSelec;

    public GameFragment() {
    }

    public void setSelec(ISelecImpl selec) {
        this.iSelec = selec;
    }

    private Drawable BLUE_DRAWABLE;


    private AtomicBoolean isInit = new AtomicBoolean(false);


    @Override
    public void loadData() {
        EventBus.getDefault().register(this);
        final List<String> stringList = Arrays.asList(AppLife.getInstance().getApplication().getResources().getStringArray(R.array.lpds_hot_game));
        final List<IInfoImpl> list = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            list.add(new AssoEntity("" + i, stringList.get(i)));
        }
        EventBus.getDefault().post(new AssoDataEntity(list, true));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(AssoDataEntity assoDataEntity) {

        if (assoDataEntity.isResult()) {
            ALL_GAMES.addAll(assoDataEntity.getList());
            HOT_GAMES.addAll(assoDataEntity.getList());
            refresh();
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getContentView(), container, false);
            ButterKnife.bind(this, contentView);

            BLUE_DRAWABLE = ActivityCompat.getDrawable(getActivity(), R.drawable.shape_blue);
            init(contentView);
            isInit.set(true);
        }
        refresh();
        return contentView;
    }

    private void init(View v) {
        getCacheSelect();

        id_cache_rc.setItemAnimator(new DefaultItemAnimator());
        id_cache_rc.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        id_cache_rc.setAdapter(new BaseQuickAdapter<IInfoImpl, BaseViewHolder>(R.layout.adapter_text, CACHE_GAMES) {

            @Override
            protected void convert(BaseViewHolder baseViewHolder, final IInfoImpl infoImpl) {

                TextView t = baseViewHolder.getView(R.id.id_txt);
                t.setText(infoImpl.getName());
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectInfo != null
                                && selectInfo.getId().equals(infoImpl.getId()) && isSameFlag(CACHE_FLAG)) {
                            return;
                        }
                        flag = CACHE_FLAG;
                        refresh();
                    }
                });
                if (isSameFlag(CACHE_FLAG) && selectInfo.getId().equals(infoImpl.getId())) {
                    t.setBackground(BLUE_DRAWABLE);
                } else if (t.getBackground() == BLUE_DRAWABLE) {
                    t.setBackground(new BitmapDrawable());
                }

            }

        });

        id_hot_game_rc.setItemAnimator(new DefaultItemAnimator());
        id_hot_game_rc.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        id_hot_game_rc.setAdapter(new BaseQuickAdapter<IInfoImpl, BaseViewHolder>(R.layout.adapter_text, HOT_GAMES) {

            @Override
            protected void convert(BaseViewHolder baseViewHolder, final IInfoImpl infoImpl) {
                TextView t = baseViewHolder.getView(R.id.id_txt);
                t.setText(infoImpl.getName());
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectInfo != null
                                && selectInfo.getId().equals(infoImpl.getId()) && isSameFlag(HOT_GAME_FLAG)) {
                            return;
                        }
                        selectInfo = infoImpl;
                        flag = HOT_GAME_FLAG;
                        refresh();

                    }
                });
                if (isSameFlag(HOT_GAME_FLAG) && selectInfo.getId().equals(infoImpl.getId())) {
                    t.setBackground(BLUE_DRAWABLE);
                } else if (t.getBackground() == BLUE_DRAWABLE) {
                    t.setBackground(new BitmapDrawable());
                }
            }

        });


    }


    public void refresh() {
        if (!isInit.get()) {
            return;
        }
        if (CACHE_GAMES.size() > 0) {
            id_cache_layout.setVisibility(View.VISIBLE);
        } else {
            id_cache_layout.setVisibility(View.GONE);
        }
        chenge();
    }


    private void chenge() {
        if (CACHE_GAMES.size() > 0) {
            id_cache_rc.getAdapter().notifyDataSetChanged();
        }
        id_hot_game_rc.getAdapter().notifyDataSetChanged();
    }

    private boolean isSameFlag(int action) {
        return flag == action;
    }

    private void getCacheSelect() {
        SharedPreferences s = getActivity().getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        String json = s.getString(XML_VAUE, "");
        if (!json.isEmpty()) {
            try {
                AssoDataEntity a = new Gson().fromJson(json, AssoDataEntity.class);
                CACHE_GAMES.addAll(a.getList());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_hot_game;
    }

    @Override
    public void finish() {

    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getTitle() {
        return "最热游戏";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }
}
