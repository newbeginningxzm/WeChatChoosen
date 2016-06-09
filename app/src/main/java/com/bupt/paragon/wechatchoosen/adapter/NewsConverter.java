package com.bupt.paragon.wechatchoosen.adapter;

import android.text.format.DateFormat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bupt.paragon.wechatchoosen.R;
import com.bupt.paragon.wechatchoosen.model.News;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/**
 * Created by Paragon on 2016/5/26.
 */
public class NewsConverter extends DataConverter<News> {
    StringBuilder builder=new StringBuilder();
    @Override
    public void convert(ViewHolder holder, News newsBean) {
        if(newsBean!=null){
            holder.setText(R.id.item_title,newsBean.getTitle());
            holder.setText(R.id.item_src,newsBean.getSource());
            holder.setText(R.id.id_item_date, formatDate(newsBean.getId()));
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

    private String formatDate(String date){
        StringBuilder builder=new StringBuilder();
        builder.delete(0,builder.length());
        builder.append(date.substring(7,15));
        builder.insert(4, '年');
        if(builder.charAt(5)=='0'){
            builder.setCharAt(5, builder.charAt(6));
            builder.setCharAt(6, '月');
        }else{
            builder.insert(7, "月");
        }
        if(builder.charAt(builder.length()-2)=='0')
            builder.delete(builder.length()-2, builder.length()-1);
        builder.append("日");
        return builder.toString();
    }

}
