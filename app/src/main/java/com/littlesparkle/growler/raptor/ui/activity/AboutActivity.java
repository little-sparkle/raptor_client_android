package com.littlesparkle.growler.raptor.ui.activity;


import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.littlesparkle.growler.library.activity.HandlerActivity;

import com.littlesparkle.growler.raptor.R;

/**
 * Created by dell on 2016/7/6.
 */
public class AboutActivity extends HandlerActivity {
    WebView mWebView = null;
    WebSettings mWebSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onHandlerMessage(Message msg) {

    }

    @Override
    public int setActivityContentView() {
        return R.layout.activity_about;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mWebView = (WebView) this.findViewById(R.id.webview_about);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

        });


        mWebView.loadUrl("http://www.baidu.com");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                this.finish();

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
