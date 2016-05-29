package com.bupt.paragon.wechatchoosen.adapter;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by Paragon on 2016/5/28.
 */
public abstract class DataConverter<T> {
    protected WeakReference<Context> mContext;
    abstract void convert(ViewHolder holder, T t);
    public void setContext(Context context){
        if(context!=null){
            if(mContext==null)
                mContext=new WeakReference<Context>(context);
        }
    }

}
