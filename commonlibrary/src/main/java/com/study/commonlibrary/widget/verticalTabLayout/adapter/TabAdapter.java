package com.study.commonlibrary.widget.verticalTabLayout.adapter;

import com.study.commonlibrary.widget.verticalTabLayout.view.TabView;

public interface TabAdapter {
    int getCount();

    TabView.TabBadge getBadge(int position);

    TabView.TabIcon getIcon(int position);

    TabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
