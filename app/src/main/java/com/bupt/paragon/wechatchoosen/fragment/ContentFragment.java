package com.bupt.paragon.wechatchoosen.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bupt.paragon.wechatchoosen.R;

/**
 * Created by Paragon on 2016/5/27.
 */
public class ContentFragment extends CurrentFragment {
    private String mContentUrl,mReadyToLoad;
    private WebView mWebView;
    private Bundle mBundle;
    private static final String TAG="ContentFragment";
    private boolean isFirstLoaded=true;


    public ContentFragment(){super();}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.content_fragment,container,false);
        mWebView= (WebView) view.findViewById(R.id.conntent_web);
        mBundle=getArguments();
        mReadyToLoad= (String) mBundle.get("url");
        mContentUrl=mReadyToLoad;
        initWebView(mWebView);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initWebView(WebView webView){

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         view.setVisibility(View.VISIBLE);
                                     }
                                 }
        );
        // 支持缩放
        WebSettings websettings = webView.getSettings();
        websettings.setBuiltInZoomControls(true);
        websettings.setDisplayZoomControls(false);// 要求API大于11

        // 去掉滚动条
        // webview.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        // 设置webview自适应屏幕大小
        websettings.setUseWideViewPort(true);
        websettings.setLoadWithOverviewMode(true);

        // 支持与javascript交互
        websettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(mContentUrl);
        Log.e(TAG,"Load Web:"+mContentUrl);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(isFirstLoaded){
//            mWebView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mWebView.loadUrl(mContentUrl);
//                    isFirstLoaded=false;
//                }
//            },200);
//        }
//    }

    public void setUrl(String url){
        mReadyToLoad=url;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(!mReadyToLoad.equals(mContentUrl)){
                mWebView.loadUrl(mReadyToLoad);
                mContentUrl=mReadyToLoad;
            }
        }
    }

    public String getCurrentUrl(){
        return mContentUrl;
    }

    public void setVisibility(boolean visible){
        if(mWebView!=null){
            if(visible)
                mWebView.setVisibility(View.VISIBLE);
            else
                mWebView.setVisibility(View.INVISIBLE);
        }
    }

}
