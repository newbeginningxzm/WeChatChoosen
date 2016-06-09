package com.bupt.paragon.wechatchoosen.presenter;

import android.content.Context;

import com.bupt.paragon.wechatchoosen.event.NewsListRefreshEvent;
import com.bupt.paragon.wechatchoosen.model.IPageBiz;
import com.bupt.paragon.wechatchoosen.model.News;
import com.bupt.paragon.wechatchoosen.model.Response;
import com.bupt.paragon.wechatchoosen.views.NewsListView;
import com.bupt.paragon.wechatchoosen.views.ViewRefreshListener;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
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
    private IPageBiz mPageBiz;

    public NewsListPresenter(NewsListView view){
        if(view==null){
            throw new IllegalArgumentException("NewsListView can't be null!");
        }

        if(!(view instanceof ViewRefreshListener)){
            throw new IllegalArgumentException("NewsListView must implements ViewRefreshListener!");
        }

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

    public void refresh(ViewRefreshListener.RefreshEvent event){
        final NewsListRefreshEvent<News> mEvent= (NewsListRefreshEvent<News>) event;
        Call<Response> call=mPageBiz.getNews(mEvent.getPage(),mEvent.getPageCount(),DATA_TYPE,KEY);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                mEvent.setmData(response.body().getResult().getList());
                ((ViewRefreshListener)mListView).onRefreshSuccess(mEvent);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                ((ViewRefreshListener)mListView).onRefreshFailed(mEvent);
            }
        });
    }
}
