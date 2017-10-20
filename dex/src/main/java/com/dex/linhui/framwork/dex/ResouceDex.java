package com.dex.linhui.framwork.dex;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lin.alllib.AppLife;

import java.io.File;

/**
 * Created by linhui on 2017/10/17.
 */
public class ResouceDex extends Resources{


    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    public ResouceDex(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }


    public static Resources newInstance(File path){
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, path.getAbsolutePath());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return new ResouceDex(assetManager, AppLife.getInstance().getApplication().getResources().getDisplayMetrics(),
                AppLife.getInstance().getApplication().getResources().getConfiguration());
    }
}
