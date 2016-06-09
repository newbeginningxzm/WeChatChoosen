package com.bupt.paragon.wechatchoosen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bupt.paragon.wechatchoosen.fragment.ContentFragment;
import com.bupt.paragon.wechatchoosen.fragment.NewsListFragment;
import com.bupt.paragon.wechatchoosen.fragment.ListFragmentListener;
import com.bupt.paragon.wechatchoosen.fragment.SetCurrentListener;
import com.bupt.paragon.wechatchoosen.model.News;


public class MainActivity extends AppCompatActivity implements ListFragmentListener<News>,SetCurrentListener{
    private int mPageCount;
    private ContentFragment mContentFragment;
    private Fragment mListFragment,mCurrentFragment;
    private static final String TAG="MainActivity";
    private FragmentManager mFragmentManager;
    private Handler mHandler=new Handler();
    private boolean isFirstChange=true;
    private static final String TAG_LISTFRAGMENT="LISTFRAGMENT",TAG_CONTENTFRAGMENT="CONTENTFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState){
        if(savedInstanceState==null){
            mFragmentManager=getFragmentManager();
            mListFragment=new NewsListFragment();
            FragmentTransaction transaction=mFragmentManager.beginTransaction()
                    .add(R.id.id_main_content, mListFragment, TAG_LISTFRAGMENT);
            transaction.commit();
        }
    }

    private void switchFragment(Fragment from,Fragment to){
        boolean isCurrent=mCurrentFragment==to;
        Log.e(TAG,"isCurrent:"+isCurrent);
        if(!isCurrent){
            FragmentTransaction transaction=mFragmentManager.beginTransaction();
            if(!to.isAdded()){
                transaction.hide(from).add(R.id.id_main_content,to,TAG_CONTENTFRAGMENT).commit();
            }
            else{
                transaction.hide(from).show(to).commit();
            }
        }
    }


    @Override
    public void onViewContent(News news) {
        Log.e(TAG, "position:" + news.getId() + " clicked!");
        if(mContentFragment==null){
            mContentFragment=new ContentFragment();
            Bundle bundle=new Bundle();
            bundle.putString("url", news.getUrl());
            Log.e(TAG, "url:" + news.getUrl());
            mContentFragment.setArguments(bundle);
        }else{
            if(news.getUrl()!=null){
                if(!news.getUrl().equals(mContentFragment.getCurrentUrl()))
                    mContentFragment.setVisibility(false);
                mContentFragment.setUrl(news.getUrl());
            }
        }
        switchFragment(mListFragment,mContentFragment);
    }


    @Override
    public void onBackPressed() {
        switch (mFragmentManager.findFragmentById(R.id.id_main_content).getTag()){
            case TAG_CONTENTFRAGMENT:
                switchFragment(mContentFragment,mListFragment);
                break;
            default:
                super.onBackPressed();
        }
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        mCurrentFragment=fragment;
        Log.e(TAG,"Cuurent Fragment:"+mCurrentFragment.getClass());
    }
}
