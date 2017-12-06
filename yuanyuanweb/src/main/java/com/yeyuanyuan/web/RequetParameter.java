package com.yeyuanyuan.web;

import android.os.Handler;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by linhui on 2017/12/5.
 */
public class RequetParameter<T extends RequestResult> implements Cloneable {

    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String GET = "GET";
    public static final String POST = "POST";

    String method = "GET";
    Request request;
    Map<String, Object> para;
    String jsonPara;
    String result;
    T object;
    boolean isOk;

    RequetParameter() {
    }


    /**
     * 功能：拼接Url和参数
     */
    public static String getNewUrl(final String url, final Map<String, Object> params) {
        if (url == null || url.length() == 0 || url.equals("null")) {
            throw new NullPointerException();
        }
        if (params != null && params.size() > 0) {
            List<NameValuePair> pairs = getPairs(params);
            String parameters = URLEncodedUtils.format(pairs, "UTF-8");
            if (parameters != null && parameters.length() > 0 && !parameters.equals("null")) {
                return url + "?" + parameters;
            }
        }
        return url;
    }

    /**
     * 排序参数
     */
    private static void sortPairs(List<NameValuePair> pairs) {
        for (int i = 1; i < pairs.size(); i++) {
            for (int j = i; j > 0; j--) {
                NameValuePair a = pairs.get(j - 1);
                NameValuePair b = pairs.get(j);
                if (compare(a.getName(), b.getName())) {
                    // 互换
                    /*String name = a.getName();
                    String value = a.getValue();

                    a.init(b.getName());
                    a.setValue(b.getValue());

                    b.init(name);
                    b.setValue(value);*/
                    NameValuePair e = new BasicNameValuePair(a.getName(), a.getValue());
                    NameValuePair f = new BasicNameValuePair(b.getName(), b.getValue());
                    pairs.set(j - 1, f);
                    pairs.set(j, e);
                }
            }
        }
    }

    /**
     * 返回true说明str1大，返回false说明str2大
     */
    private static boolean compare(String a, String b) {
        String a1 = a.toUpperCase();
        String b1 = b.toUpperCase();
        boolean flag = true;
        int minLen = 0;
        if (b.length() < b.length()) {
            minLen = b.length();
            flag = false;
        } else {
            minLen = b.length();
            flag = true;
        }
        for (int index = 0; index < minLen; index++) {
            char a2 = a1.charAt(index);
            char b2 = b1.charAt(index);
            if (a2 != b2) {
                if (a2 > b2) {
                    return true; // a大
                } else {
                    return false; // b大
                }
            }
        }
        return flag;
    }

    /**
     * 功能：转化参数
     */
    private static List<NameValuePair> getPairs(Map<String, Object> map) {
        List<NameValuePair> params = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = "";
                String value = "";
                try {
                    key = entry.getKey().trim();
                    value = entry.getValue().toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NameValuePair pair = new BasicNameValuePair(key, value);
                params.add(pair);
            }
        }
        sortPairs(params);
        return params;
    }

    @Override
    public String toString() {
        return "RequetEntity{" +
                "request=" + request +
                ", para=" + para +
                ", result='" + result + '\'' +
                ", object =" + object +
                ", url =" + getUri() +
                '}';
    }

    public String getUri() {
        return request != null ? request.url().uri().toString() : "";
    }

    public Map getPara() {
        return para;
    }

    public String getResult() {
        return result;
    }

    public Request getRequest() {
        return request;
    }

    public T getObject() {
        return object;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void execute() {
        Zygote.execute(this);
    }

    public void asyncExecute() {
        Zygote.asyncExecute(this);
    }
}
