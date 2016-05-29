package com.bupt.paragon.wechatchoosen.adapter;

/**
 * Created by Paragon on 2016/5/28.
 */
public interface DataConverter<T> {
    void convert(ViewHolder holder, T t);
}
