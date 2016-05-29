package com.bupt.paragon.wechatchoosen.adapter;

import com.bupt.paragon.wechatchoosen.R;
import com.bupt.paragon.wechatchoosen.model.News;

/**
 * Created by Paragon on 2016/5/26.
 */
public class NewsConverter implements DataConverter<News> {
    @Override
    public void convert(ViewHolder holder, News newsBean) {
        if(newsBean!=null){
            holder.setText(R.id.item_title,newsBean.getTitle());
            holder.setText(R.id.item_src,newsBean.getSource());
        }else{
            holder.setText(R.id.item_title,"Default Title");
            holder.setText(R.id.item_src,"Default Src");
        }
    }
}
