package com.study.commonlibrary.list;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.study.commonlibrary.R;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.adapter.FragmentAdapter;
import com.study.commonlibrary.base.manage.ZController;
import com.study.commonlibrary.base.view.BaseFragment;
import com.study.commonlibrary.listener.OnBindViewListener;
import com.study.commonlibrary.listener.OnViewClickListener;

import java.util.List;

/**
 * Author:zx on 2019/9/2419:18
 */
public class ZViewpageDialog extends ZDialog {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private static volatile ZViewpageDialog mDialog;

    private static ZViewpageDialog getInstance() {
        if (null == mDialog) {
            synchronized (ZViewpageDialog.class) {
                if (null == mDialog) {
                    mDialog = new ZViewpageDialog();
                }
            }
        }
        return mDialog;
    }

    @Override
    protected void initView(View view) {
        mTabLayout = view.findViewById(R.id.dialog_table_layout);
        mViewPager = view.findViewById(R.id.dialog_viewpager);
        if (null == mViewPager) {
            throw new IllegalArgumentException("自定义类别xml布局，请设置ViewPager的控件id为dialog_viewpager");
        }
        mAdapter = new FragmentAdapter(getChildFragmentManager(), zController.getFragments());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        dismissFragment();
    }

    public void dismissFragment() {
        if (null != zController.getFragments()) {
            List<Fragment> list = zController.getFragments();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof BaseFragment) {
                    ((BaseFragment) list.get(i)).setZDialog(this);
                }
            }
        }
    }

    /*********************************************************************
     * 使用Builder模式实现
     *
     */
    public static class Builder {
        ZController.ZParams params;

        public Builder(FragmentManager fragmentManager) {
            params = new ZController.ZParams();
            params.mFragmentManager = fragmentManager;
            params.mIsInstance = true;
        }

        public ZViewpageDialog.Builder setLayoutRes(@LayoutRes int layoutRes) {
            params.mLayoutRes = layoutRes;
            return this;
        }

        /**
         * 设置弹窗宽度是屏幕宽度的比例 0 -1
         */
        public ZViewpageDialog.Builder setScreenWidthAspect(Context context, float widthAspect) {
            params.mWidth = (int) (getScreenWidth(context) * widthAspect);
            return this;
        }

        public ZViewpageDialog.Builder setWidth(int widthPx) {
            params.mWidth = widthPx;
            return this;
        }

        /**
         * 设置屏幕高度比例 0 -1
         */
        public ZViewpageDialog.Builder setScreenHeightAspect(Context context, float heightAspect) {
            params.mHeight = (int) (getScreenHeight(context) * heightAspect);
            return this;
        }

        public ZViewpageDialog.Builder setHeight(int heightPx) {
            params.mHeight = heightPx;
            return this;
        }

        public ZViewpageDialog.Builder setGravity(int gravity) {
            params.mGravity = gravity;
            return this;
        }

        public ZViewpageDialog.Builder setDialogX(int x) {
            params.mDialogX = x;
            return this;
        }

        public ZViewpageDialog.Builder setDialogY(int y) {
            params.mDialogY = y;
            return this;
        }

        public ZViewpageDialog.Builder setCancelOutside(boolean cancel) {
            params.mIsCancelableOutside = cancel;
            return this;
        }

        public ZViewpageDialog.Builder setDimAmount(float dim) {
            params.mDimAmount = dim;
            return this;
        }

        public ZViewpageDialog.Builder setTag(String tag) {
            params.mTag = tag;
            return this;
        }

        public ZViewpageDialog.Builder setOnBindViewListener(OnBindViewListener listener) {
            params.mBindViewListerner = listener;
            return this;
        }

        public ZViewpageDialog.Builder addOnClickListener(int... ids) {
            params.ids = ids;
            return this;
        }

        public ZViewpageDialog.Builder setOnViewClickListener(OnViewClickListener listener) {
            params.mOnViewClickListener = listener;
            return this;
        }

        public ZViewpageDialog.Builder setOnDimissListener(DialogInterface.OnDismissListener dimissListener) {
            params.mOnDismissListenre = dimissListener;
            return this;
        }

        //设置弹窗动画
        public ZViewpageDialog.Builder setDialogAnimationRes(int animationRes) {
            params.mDialogAnimationRes = animationRes;
            return this;
        }

        //设置fragments
        public ZViewpageDialog.Builder setFragments(List<Fragment> list) {
            params.fragments = list;
            return this;
        }
        public ZViewpageDialog.Builder setIsInstance(boolean isInstance) {
            params.mIsInstance = isInstance;
            return this;
        }
        public ZViewpageDialog create() {
            ZViewpageDialog dialog = params.mIsInstance ? ZViewpageDialog.getInstance() : new ZViewpageDialog();
            params.apply(dialog.zController);
            return dialog;
        }
    }
}
