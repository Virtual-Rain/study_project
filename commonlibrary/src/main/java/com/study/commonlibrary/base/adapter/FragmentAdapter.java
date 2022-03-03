package com.study.commonlibrary.base.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.study.commonlibrary.base.view.BaseFragment;
import com.study.commonlibrary.list.ZViewpageDialog;

import java.util.List;

/**
 * Author:zx on 2019/9/2419:37
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
