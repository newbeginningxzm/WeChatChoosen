package com.bupt.paragon.wechatchoosen.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bupt.paragon.wechatchoosen.NewsLruCacheMap;
import com.bupt.paragon.wechatchoosen.R;
import com.bupt.paragon.wechatchoosen.adapter.DataConverter;
import com.bupt.paragon.wechatchoosen.adapter.MultiItemTypeSupport;
import com.bupt.paragon.wechatchoosen.adapter.NewsConverter;
import com.bupt.paragon.wechatchoosen.adapter.NewsListAdapter;
import com.bupt.paragon.wechatchoosen.event.NewsListRefreshEvent;
import com.bupt.paragon.wechatchoosen.model.News;
import com.bupt.paragon.wechatchoosen.presenter.NewsListPresenter;
import com.bupt.paragon.wechatchoosen.views.NewsListView;
import com.bupt.paragon.wechatchoosen.views.ViewRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends CurrentFragment implements ViewRefreshListener, NewsListView {
    private PullToRefreshListView mNewsList;
    private DataConverter<News> mConveter;
    private NewsListAdapter<News> mAdapter;
    private ArrayList<News> mData=new ArrayList<>();
    private NewsLruCacheMap mLoaded=new NewsLruCacheMap();
    private ListFragmentListener<News> mListFragmentListener;
    private int mPage,mPageCount=10;
    private static final String TAG="NewsListFragment";
    public static final int REFRESH=1,LOADMORE=2;
    private NewsListPresenter mPresenter=new NewsListPresenter(this);

    private Handler mHandler;
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
        mNewsList.setRefreshing(true);
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
                Log.e(TAG, "Refresh In Resume!");
            }
        }, 200);
    }

    private void refreshNewsList(List<News> news,int page,int pageCount,int type){
        if(news!=null&&news.size()!=0){
            Log.e("MainActivity", "Get Refresh:" + news.size());
            switch (type){
                case LOADMORE:{
                    for(int j=0;j<news.size();j++){
                        if(!mLoaded.containsKey(news.get(j).getTitle())){
                            mData.add(news.get(j)); //不包含则加入List
                            mLoaded.put(news.get(j).getTitle());   //将新的News加入缓存
                        }
                    }
                }
                break;
                case REFRESH:{
                    mData.clear();
                    mPage=1;
                    mLoaded.clear();
                    for(int j=0;j<news.size();j++ ){
                        mData.add(news.get(j));
                        mLoaded.put(news.get(j).getTitle());
                    }
                }
            }
        }
    }

    @Override
    public int getCurrentPage() {
        return mPage;
    }

    @Override
    public int getCurrentPageCount() {
        return mPageCount;
    }

    @Override
    public void onRefreshSuccess(RefreshEvent event) {
        NewsListRefreshEvent<News> mEvent= (NewsListRefreshEvent<News>) event;
        refreshNewsList(mEvent.getmData(), mEvent.getPage(),mEvent.getPage(),mEvent.getType());
        Log.e(TAG, "Get Response:"+mPage);
        mAdapter.notifyDataSetChanged();
        mNewsList.onRefreshComplete();
    }

    @Override
    public void onRefreshFailed(RefreshEvent event) {
        Toast.makeText(getActivity(),"Network Error!",Toast.LENGTH_LONG).show();
        mNewsList.onRefreshComplete();
    }

    private void loadNews(int page,int pageCount,int callType){
        NewsListRefreshEvent<News> event = new NewsListRefreshEvent.Builder<News>()
                .setPage(page)
                .setPageCount(pageCount)
                .setType(callType)
                .build();
        mPresenter.refresh(event);
    }

    private void refreshData(){
        loadNews(0,mPageCount, REFRESH);
    }

    private void loadMore(){
        loadNews(mPage+1,mPageCount,LOADMORE);
    }


}
