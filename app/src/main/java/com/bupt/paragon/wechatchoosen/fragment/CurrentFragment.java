package com.bupt.paragon.wechatchoosen.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Paragon on 2016/5/29.
 */
public class CurrentFragment extends Fragment{
    private SetCurrentListener mSetCurrentListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof SetCurrentListener)
            mSetCurrentListener= (SetCurrentListener) getActivity();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
            mSetCurrentListener.setCurrentFragment(this);
    }
}
