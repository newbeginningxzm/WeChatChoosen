package com.bupt.paragon.wechatchoosen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bupt.paragon.wechatchoosen.R;
import com.bupt.paragon.wechatchoosen.model.News;

/**
 * Created by Paragon on 2016/5/26.
 */
public class NewsConverter extends DataConverter<News> {

    @Override
    public void convert(ViewHolder holder, News newsBean) {
        if(newsBean!=null){
            holder.setText(R.id.item_title,newsBean.getTitle());
            holder.setText(R.id.item_src,newsBean.getSource());
            Glide.with(mContext.get())
                    .load(newsBean.getFirstImg())
                    .asBitmap()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.newimage_default)
                    .into(holder.<ImageView>getView(R.id.item_firstImg));
        }else{
            holder.setText(R.id.item_title,"Default Title");
            holder.setText(R.id.item_src,"Default Src");
        }
    }
}
