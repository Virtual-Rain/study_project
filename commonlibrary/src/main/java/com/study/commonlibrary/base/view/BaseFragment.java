package com.study.commonlibrary.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.contract.ApolloBinder;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.uitls.ToastUtil;
import com.study.commonlibrary.widget.LoadingMaker;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:zx on 2019/9/2716:35
 */
public abstract class BaseFragment extends Fragment {
    private FragmentActivity activity;
    private Unbinder unbinder;
    private ApolloBinder apolloBinder;
    protected InputMethodManager inputMethodManager;
    protected View rootView;
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    private boolean isLoading;
    // 界面是否已创建完成
    private boolean isViewCreated;
    // 是否对用户可见
    private boolean isVisibleToUser;
    // 数据是否已请求
    private boolean isDataLoaded;
    //当前的DialogFragment
    public ZDialog mDialog;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        tryLoadData();
    }

    private void tryLoadData() {
        if (isViewCreated && isVisibleToUser && !isDataLoaded) {
            initData();
            initListener();
            isDataLoaded = true;
        }
    }

    protected void initData() {
    }


    protected void initListener() {
    }

    public void setZDialog(ZDialog dialog) {
        mDialog = dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutId(), container, false);
        // 返回一个Unbinder对象
        unbinder = ButterKnife.bind(this, rootView);
        apolloBinder = Apollo.bind(this);

        //可见，但是并没有加载过
        if (isFragmentVisible && !isLoading) {
            onFragmentVisibleChange(true);
        }
        return rootView;

    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputMethodManager = (InputMethodManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != apolloBinder) {
            if (apolloBinder.isUnbind()) {
                apolloBinder = null;
                apolloBinder = Apollo.bind(this);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();

        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isLoading && isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }

    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (apolloBinder != null) {
            apolloBinder.unbind();
            apolloBinder = null;
        }
        super.onDestroyView();
       /* if ( rootView!= null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }*/
    }

    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void onStop() {
        dismiss();
        super.onStop();
    }

    /**
     * 布局文件
     *
     * @return @resourcesId xml
     */
    protected abstract int getLayoutId();


    protected FragmentActivity getMactivity() {
        return activity;
    }

    public void loading() {
        activity.runOnUiThread(() -> LoadingMaker.getInstance().showProgressDialog(getContext()));
    }

    public void dismiss() {
        LoadingMaker.getInstance().dismissProgressDialog();
    }


    /**
     * 提示
     *
     * @param msg 提示信息
     */
    public void showInfo(final String msg) {
        activity.runOnUiThread(() -> ToastUtil.showInfo(msg));
    }

    /**
     * 提示警告信息
     *
     * @param msg
     */
    public void showWarning(final String msg) {
        activity.runOnUiThread(() -> ToastUtil.showWarning(msg));
    }

    /**
     * 提示成功信息
     *
     * @param msg
     */
    public void showSuccess(final String msg) {
        activity.runOnUiThread(() -> ToastUtil.showSuccess(msg));

    }

    /**
     * 提示错误信息
     *
     * @param msg
     */
    public void showError(int code, String msg) {
        activity.runOnUiThread(() -> ToastUtil.showError(msg));
    }

    /**
     * 重新设置备用接口地址
     */
    protected abstract void onReloadUrl();

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (!isLoading) {
            isLoading = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void dismissDialogFragment() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
