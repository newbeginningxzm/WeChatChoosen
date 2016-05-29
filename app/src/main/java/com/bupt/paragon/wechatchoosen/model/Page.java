package com.bupt.paragon.wechatchoosen.model;

import java.util.List;

/**
 * Created by Paragon on 2016/5/27.
 */
public class Page {
    private List<News> list;
    private int totalPage;
    private int ps;
    private int pno;

    public int getPageCount() {
        return ps;
    }

    public List<News> getList() {
        return list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPage() {
        return pno;
    }
}
