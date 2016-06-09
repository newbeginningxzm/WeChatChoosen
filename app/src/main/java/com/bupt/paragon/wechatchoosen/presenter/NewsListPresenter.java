package com.bupt.paragon.wechatchoosen.presenter;

import android.content.Context;

import com.bupt.paragon.wechatchoosen.model.IPageBiz;
import com.bupt.paragon.wechatchoosen.views.NewsListView;

import java.lang.ref.WeakReference;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paragon on 2016/6/9.
 */
public class NewsListPresenter {
    private static final String URL_PAGE="http://v.juhe.cn/weixin/";
    private static final String KEY="8192f5f325d39561037dd6342fead98b";
    private static final String DATA_TYPE="json";
    private static final String TAG="NewsListPresenter";
    private NewsListView mListView;
    private Retrofit mRetrofit;
    private WeakReference<Context> mContext;
    private IPageBiz mPageBiz;

    public NewsListPresenter(NewsListView view,Context context){
        if(view==null){
            throw new IllegalArgumentException("NewsListView can't be null!");
        }
        if(context==null){
            throw new IllegalArgumentException("Context can't be null!");
        }
        mContext=new WeakReference<>(context);
        mListView=view;
        initPresenter();
    }

    public void initPresenter() {
        mRetrofit=new Retrofit.Builder()
                .baseUrl(URL_PAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mPageBiz=mRetrofit.create(IPageBiz.class);
    }

    public void refresh(){}

    public void loadMore(){}


}
