package com.bupt.paragon.wechatchoosen.adapter;

import android.content.Context;

import com.bupt.paragon.wechatchoosen.model.News;

import java.util.List;

/**
 * Created by Paragon on 2016/5/28.
 */
public class NewsListAdapter<T> extends MultiItemCommonAdapter<T>{
    private NewsConverter mConveter;
    public NewsListAdapter(Context context, List<T> datas,
                           MultiItemTypeSupport<T> multiItemTypeSupport,DataConverter<T> converter){
        super(context,datas,multiItemTypeSupport);
        this.mConveter= (NewsConverter) converter;
        mConveter.setContext(context);
    }

    @Override
    public void convert(ViewHolder holder, T t) {
        mConveter.convert(holder, (News) t);
    }
}
