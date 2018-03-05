package com.mr.huang.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;

import com.mr.huang.R;
import com.mr.huang.data.entity.BaseXmlEntity;
import com.mr.huang.data.entity.XmlTestEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by linhui on 2018/1/5.
 */
public class TestActivity extends AppCompatActivity {


    private static final String TAG = "TActivity";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        try {
        AssetManager assetManager = getAssets();
        InputStream inputStream  = assetManager.open("test");
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, "utf-8");

            tchangeXml(xmlPullParser, XmlTestEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends BaseXmlEntity> void tchangeXml(XmlPullParser xmlPullParser, Class<T> tClass) throws Exception {

        T entity =  tClass.newInstance();

        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    Log.i(TAG, "START_DOCUMENT: ");
                    break;
                case XmlPullParser.START_TAG:
                    try {
                        Log.i(TAG, "START_TAG: name = "+xmlPullParser.getName()+", getText() "+xmlPullParser.nextText());
                    }catch (Exception ex){
                        Log.i(TAG, "START_TAG: name = "+xmlPullParser.getName()+", getText() null");
                    }

                    break;
                case XmlPullParser.END_TAG:
                    Log.i(TAG, "END_TAG: ");
                    break;
            }
            eventType = xmlPullParser.next();
        }
        Log.i(TAG, "END_DOCUMENT: ");

    }


    public static void main(String[] args){
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));



        System.out.println("http://define.ifeimo.com/fmstart_lpds.def".split("/")[2]);

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMEssage(Object a){

    }

}
