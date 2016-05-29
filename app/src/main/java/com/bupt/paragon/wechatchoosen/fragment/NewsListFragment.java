package com.bupt.paragon.wechatchoosen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bupt.paragon.wechatchoosen.R;
import com.bupt.paragon.wechatchoosen.adapter.DataConverter;
import com.bupt.paragon.wechatchoosen.adapter.MultiItemTypeSupport;
import com.bupt.paragon.wechatchoosen.adapter.NewsConverter;
import com.bupt.paragon.wechatchoosen.adapter.NewsListAdapter;
import com.bupt.paragon.wechatchoosen.model.IPageBiz;
import com.bupt.paragon.wechatchoosen.model.News;
import com.bupt.paragon.wechatchoosen.model.Response;
import com.bupt.paragon.wechatchoosen.model.Result;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paragon on 2016/5/28.
 */
public class NewsListFragment extends CurrentFragment{
    private PullToRefreshListView mNewsList;
    private DataConverter<News> mConveter;
    private NewsListAdapter<News> mAdapter;
    private ArrayList<News> mData=new ArrayList();
    private ListFragmentListener<News> mListFragmentListener;
    private int mPage;
    private static final String URL_PAGE="http://v.juhe.cn/weixin/";
    private static final String KEY="8192f5f325d39561037dd6342fead98b";
    private static final String DATA_TYPE="json";
    private static final String TAG="NewsListFragment";
    private Handler mHandler;
    private PageCallBack  mRefreshCallBack=new PageCallBack(0,10,PageCallBack.REFRESH);
    private AdapterView.OnItemClickListener mOnItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG,"Clicked:"+position);
            mListFragmentListener.onViewContent(mData.get(position-1));
        }
    };
    private MultiItemTypeSupport<News> mSupporter=new MultiItemTypeSupport<News>() {
        @Override
        public int getLayoutId(int position, News news) {
            return R.layout.content_item;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position, News news) {
            return 0;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.newslist_frament,container,false);
        mNewsList= (PullToRefreshListView) view.findViewById(R.id.id_newslist);
        mNewsList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshData();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
        mNewsList.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler=new Handler(getActivity().getMainLooper());
        mListFragmentListener = (ListFragmentListener<News>) getActivity();
        mConveter=new NewsConverter();
        mNewsList.setOnItemClickListener(mOnItemClickListener);
        mAdapter=new NewsListAdapter<>(getActivity(),mData,mSupporter,mConveter);
        mNewsList.setAdapter(mAdapter);
        Log.e(TAG, "Set Adapter!");
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
                Log.e(TAG,"Refresh In Resume!");
            }
        },200);
    }

    private void refreshNewsList(List<News> news,int page,int pageCount){
        if(news!=null&&news.size()!=0){
            Log.e("MainActivity", "Get Refresh:" + news.size());
            for(int i=(page-1)*pageCount,j=0;j<news.size();i++,j++){
                if(i<mData.size()){
                    News inData=mData.get(i);
                    News refresh=news.get(j);
                    if(!refresh.getId().equals(inData.getId()))
                        mData.set(i,refresh);
                }else{
                    mData.add(news.get(j));
                }
            }
        }
    }
    private class PageCallBack  implements Callback<Response> {
        public static final int REFRESH=1,LOAD_MORE=2;
        private int mPage;
        private int mPageCount;
        private int mCallType;
        public void setmCallType(int mCallType) {
            this.mCallType = mCallType;
        }

        public PageCallBack(int page,int pageCount,int callType) {
            super();
            this.mPage=page;
            this.mPageCount=pageCount;
            this.mCallType=callType;
        }

        @Override
        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
            Result mResult=response.body().getResult();
            final List<News> mRefreshData=mResult.getList();
            NewsListFragment.this.mPage=mResult.getPage();
            refreshNewsList(mRefreshData, NewsListFragment.this.mPage, mResult.getPageCount());
            Log.e(TAG, "Get Response!");
            mAdapter.notifyDataSetChanged();
            mNewsList.onRefreshComplete();
        }

        @Override
        public void onFailure(Call<com.bupt.paragon.wechatchoosen.model.Response> call, Throwable t) {

        }
        public int getPageCount() {
            return mPageCount;
        }

        public void setPageCount(int mPageCount) {
            this.mPageCount = mPageCount;
        }

        public int getPage() {
            return mPage;
        }

        public void setPage(int mPage) {
            this.mPage = mPage;
        }
    }
    private void loadNews(int page,int pageCount,int callType){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL_PAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IPageBiz newsBiz=retrofit.create(IPageBiz.class);
        Call<com.bupt.paragon.wechatchoosen.model.Response> call= newsBiz.getNews(page,pageCount,DATA_TYPE,KEY);
        mRefreshCallBack.setPage(page);
        mRefreshCallBack.setPageCount(pageCount);
        mRefreshCallBack.setmCallType(callType);
        call.enqueue(mRefreshCallBack);
    }

    private void refreshData(){
        loadNews(0, 10, PageCallBack.REFRESH);
    }

    private void loadMore(){
        loadNews(mPage+1,10,PageCallBack.LOAD_MORE);
    }
}
