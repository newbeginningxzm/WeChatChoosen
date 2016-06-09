package com.bupt.paragon.wechatchoosen.views;

/**
 * Created by Paragon on 2016/6/9.
 */
public interface ViewRefreshListener {
    interface RefreshEvent{}
    void onRefreshSuccess(RefreshEvent event);
    void onRefreshFailed(RefreshEvent event);
}
