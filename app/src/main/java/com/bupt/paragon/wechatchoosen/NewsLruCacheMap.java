package com.bupt.paragon.wechatchoosen;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Paragon on 2016/5/29.
 */
public class NewsLruCacheMap extends LinkedHashMap<String,Object>{
    private static final byte[] FLAG=new byte[0];
    private static final int DEFAULT_SIZE=32;
    private static final float DEFAULT_LOADFACTOR=0.75f;
    public NewsLruCacheMap(){
        super(DEFAULT_SIZE,DEFAULT_LOADFACTOR,false);
    }

    public NewsLruCacheMap(int initialCapacity) {
        super(initialCapacity,DEFAULT_LOADFACTOR,false);
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, Object> eldest) {
        if(size()<DEFAULT_SIZE)
            return false;
        else
            return true;
    }

    public void put(String str){
        super.put(str,FLAG);
    }

}
