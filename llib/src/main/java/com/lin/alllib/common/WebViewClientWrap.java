package com.lin.alllib.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linhui on 2017/11/22.
 * 解决微信支付宝支付功能
 */
@TargetApi(Build.VERSION_CODES.N)
public class WebViewClientWrap extends WebViewClient {

    private WebViewClient mWebViewClientImp;
    private Context context;

    public static WebViewClient wrap(Context context,WebViewClient mWebViewClientImp){
        return new WebViewClientWrap(context,mWebViewClientImp);
    }

    private WebViewClientWrap(Context context,WebViewClient mWebViewClientImp) {
        this.mWebViewClientImp = mWebViewClientImp;
        this.context = context;
    }


    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        mWebViewClientImp.onReceivedLoginRequest(view, realm, account, args);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        mWebViewClientImp.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        mWebViewClientImp.onUnhandledKeyEvent(view, event);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return mWebViewClientImp.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        mWebViewClientImp.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        mWebViewClientImp.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        mWebViewClientImp.onReceivedSslError(view, handler, error);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        mWebViewClientImp.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        mWebViewClientImp.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        mWebViewClientImp.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        mWebViewClientImp.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        mWebViewClientImp.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        mWebViewClientImp.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return mWebViewClientImp.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return mWebViewClientImp.shouldInterceptRequest(view, url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        mWebViewClientImp.onPageCommitVisible(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        mWebViewClientImp.onLoadResource(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mWebViewClientImp.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mWebViewClientImp.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return mWebViewClientImp.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url == null) return false;
        if (checkAliPay(url)) {
            return true;
        } else if (checkWeixinPay(view,url)) {
            return true;
        }else if(url.contains("https://wx.tenpay.com/cgi-bin")){
            return false;
        }

        return mWebViewClientImp.shouldOverrideUrlLoading(view,url);
    }

    private boolean checkWeixinPay(WebView webView,String url) {
        if (url.startsWith("weixin://wap/pay?")) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }else if(url.contains("wechatWapPay")){
            Map map = new HashMap();
            map.put("Referer","http://gc.gzyoufa.com/m/playgame.html");
            webView.loadUrl(url,map);
            return true;
        }
        return false;
    }

    private boolean checkAliPay(String url) {
        if (url.startsWith("alipays://") ||
                url.startsWith("mailto://") || url.startsWith("tel://")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
