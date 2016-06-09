package com.bupt.paragon.wechatchoosen.event;

import com.bupt.paragon.wechatchoosen.views.ViewRefreshListener;

import java.util.List;

/**
 * Created by Paragon-xzm on 2016/6/9.
 */
public class  NewsListRefreshEvent<T> implements ViewRefreshListener.RefreshEvent{
    private List<T> mData;
    private int page;
    private int pageCount;
    private int type;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getmData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class Builder<V>{
        private NewsListRefreshEvent<V> refreshEvent;
        private List<V> mData;
        private int page;
        private int pageCount;
        private int type;


        public Builder setData(List<V> data){
            this.mData=data;
            return this;
        }

        public Builder setType(int type){
            this.type=type;
            return this;
        }

        public Builder setPage(int page){
            this.page=page;
            return this;
        }

        public Builder setPageCount(int pageCount){
            this.pageCount=pageCount;
            return this;
        }

        public NewsListRefreshEvent<V> build(){
            refreshEvent=new NewsListRefreshEvent<>();
            refreshEvent.mData=this.mData;
            refreshEvent.page=this.page;
            refreshEvent.pageCount=this.pageCount;
            refreshEvent.type=this.type;
            return refreshEvent;
        }
    }
}
