package com.study.common;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.common.adapter.LabelSelectAdapter;
import com.study.common.entity.CheckBean;
import com.study.common.router.ARouterPath;
import com.study.commonlibrary.widget.verticalTabLayout.VerticalTab2Layout;
import com.study.commonlibrary.widget.verticalTabLayout.view.TabView;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;

@Route(path = ARouterPath.VIEW_PAGER_ACTIVITY)
public class ViewPagerActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<CheckBean> mData = new ArrayList<>();

    private ViewPager2 viewPager2;
    private VerticalTab2Layout verticalTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager2 = findViewById(R.id.vertical_viewpager);
        verticalTabLayout = findViewById(R.id.vertical_tabLayout);
        initViewPager();
        initTab();
    }

    private void initViewPager() {
//        String title = "ContentFragment";
//        fragmentList.add(ContentFragment.newInstance(title + 1, 1));
//        fragmentList.add(ContentFragment.newInstance(title + 2, 2));
//        fragmentList.add(ContentFragment.newInstance(title + 3, 3));
//        fragmentList.add(ContentFragment.newInstance(title + 4, 4));
//        fragmentList.add(ContentFragment.newInstance(title + 5, 5));
//        viewPager2.setAdapter(new FragmentStateAdapter(this) {
//            @NonNull
//            @Override
//            public Fragment createFragment(int position) {
//                return ContentFragment.newInstance(title + 1, 1);
//            }
//
//            @Override
//            public int getItemCount() {
//                return 10;
//            }
//        });
//        viewPager2.setOverScrollMode(View.OVER_SCROLL_NEVER);

        /*RecycleView*/
        initData();
        viewPager2.setAdapter(new LabelSelectAdapter(R.layout.item_label_select, mData));
    }

    private void initTab() {
        verticalTabLayout.setupWithViewPager(viewPager2, null);
        verticalTabLayout.setTabBadge(2, -1);
        verticalTabLayout.setTabBadge(8, -1);
        verticalTabLayout.getTabAt(3).setBadge(new TabView.TabBadge.Builder().setBadgeGravity(Gravity.START | Gravity.TOP)
                .setBadgeNumber(999)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (dragState == STATE_SUCCEED) {
                            badge.setBadgeNumber(-1).stroke(0xFFFFFFFF, 1, true);
                        }
                    }
                }).build());
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            if (mData.size() > 30) {
                break;
            }
            mData.add(new CheckBean("Test", true));
        }
    }
}
