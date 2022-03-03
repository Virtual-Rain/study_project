package com.study.commonlibrary.list;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.commonlibrary.R;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.adapter.ZBaseAdapter;
import com.study.commonlibrary.base.manage.ZController;
import com.study.commonlibrary.listener.OnBindViewListener;
import com.study.commonlibrary.listener.OnViewClickListener;
import com.study.commonlibrary.widget.SpaceItemDecoration;

/**
 * Author:zx on 2019/9/1819:31
 */
public class ZListDialog extends ZDialog {
    private static volatile ZListDialog mDialog;

    private static ZListDialog getInstance() {
        if (null == mDialog) {
            synchronized (ZListDialog.class) {
                if (null == mDialog) {
                    mDialog = new ZListDialog();
                }
            }
        }
        return mDialog;
    }

    @Override
    protected void bindView(View view) {
        super.bindView(view);
        //设置列表
        if (null != zController.getAdapter()) {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            if (recyclerView == null) {
                throw new IllegalArgumentException("自定义类别xml布局，请设置RecycleView的控件id为recycler_view");
            }
            zController.getAdapter().setZDialog(this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), zController.getOrientation(), false);
            if (null != zController.getLayoutManager()) {
                layoutManager = zController.getLayoutManager();
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new SpaceItemDecoration(10));
            recyclerView.setAdapter(zController.getAdapter());
            zController.getAdapter().notifyDataSetChanged();
            if (null != zController.getAdapterItemClickListener()) {
                zController.getAdapter().setOnAdapterItemClickListener(zController.getAdapterItemClickListener());
            }
        } else {
            Log.d("TDialog", "列表弹窗需要先调用setAdapter()方法!");
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

        public ZListDialog.Builder setLayoutRes(@LayoutRes int layoutRes) {
            params.listLayoutRes = layoutRes;
            return this;
        }

        //设置自定义列表布局和方向
        public ZListDialog.Builder setListLayoutRes(@LayoutRes int layoutRes, int orientaion) {
            params.listLayoutRes = layoutRes;
            params.orientation = orientaion;
            return this;
        }

        /**
         * 设置弹窗宽度是屏幕宽度的比例 0 -1
         */
        public ZListDialog.Builder setScreenWidthAspect(Context context, float widthAspect) {
            params.mWidth = (int) (getScreenWidth(context) * widthAspect);
            return this;
        }

        public ZListDialog.Builder setWidth(int widthPx) {
            params.mWidth = widthPx;
            return this;
        }

        /**
         * 设置屏幕高度比例 0 -1
         */
        public ZListDialog.Builder setScreenHeightAspect(Context context, float heightAspect) {
            params.mHeight = (int) (getScreenHeight(context) * heightAspect);
            return this;
        }

        public ZListDialog.Builder setHeight(int heightPx) {
            params.mHeight = heightPx;
            return this;
        }

        public ZListDialog.Builder setGravity(int gravity) {
            params.mGravity = gravity;
            return this;
        }

        public ZListDialog.Builder setDialogX(int x) {
            params.mDialogX = x;
            return this;
        }

        public ZListDialog.Builder setDialogY(int y) {
            params.mDialogY = y;
            return this;
        }

        public ZListDialog.Builder setCancelOutside(boolean cancel) {
            params.mIsCancelableOutside = cancel;
            return this;
        }

        public ZListDialog.Builder setDimAmount(float dim) {
            params.mDimAmount = dim;
            return this;
        }

        public ZListDialog.Builder setTag(String tag) {
            params.mTag = tag;
            return this;
        }

        public ZListDialog.Builder setOnBindViewListener(OnBindViewListener listener) {
            params.mBindViewListerner = listener;
            return this;
        }

        public ZListDialog.Builder addOnClickListener(int... ids) {
            params.ids = ids;
            return this;
        }

        public ZListDialog.Builder setOnViewClickListener(OnViewClickListener listener) {
            params.mOnViewClickListener = listener;
            return this;
        }

        //列表数据，需要传入数据和Adapter以及item点击事件
        public <A extends ZBaseAdapter> ZListDialog.Builder setAdatper(A adatper) {
            params.adapter = adatper;
            return this;
        }

        public ZListDialog.Builder setOnAdapterItemClickListener(ZBaseAdapter.OnAdapterItemClickListener listener) {
            params.mAdapterItemClickListener = listener;
            return this;
        }

        public ZListDialog.Builder setOnDimissListener(DialogInterface.OnDismissListener dimissListener) {
            params.mOnDismissListenre = dimissListener;
            return this;
        }

        public ZListDialog.Builder setLayoutManage(RecyclerView.LayoutManager layoutManager) {
            params.mLayoutManager = layoutManager;
            return this;
        }

        public ZListDialog.Builder setIsInstance(boolean isInstance) {
            params.mIsInstance = isInstance;
            return this;
        }

        public ZListDialog create() {
            ZListDialog dialog = params.mIsInstance ? ZListDialog.getInstance() : new ZListDialog();
            params.apply(dialog.zController);
            return dialog;
        }
    }
}
