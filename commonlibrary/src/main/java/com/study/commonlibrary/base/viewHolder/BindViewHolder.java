package com.study.commonlibrary.base.viewHolder;

import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.commonlibrary.ZDialog;

import butterknife.BindView;

/**
 * Author:zx on 2019/9/1909:21
 */
public class BindViewHolder extends RecyclerView.ViewHolder {

    private View bindView;
    private SparseArray<View> views;
    private ZDialog dialog;

    public BindViewHolder(@NonNull View itemView) {
        super(itemView);
        this.bindView = itemView;
        this.views = new SparseArray<>();
    }

    public BindViewHolder(View view, ZDialog dialog) {
        super(view);
        this.bindView = view;
        this.dialog = dialog;
        views = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (null == view) {
            view = bindView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BindViewHolder addOnClickListener(@IdRes int viewId) {
        final View view = getView(viewId);
        if (null != view) {
            if (view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null!=dialog){
                        if (null != dialog.getOnViewClickListener()) {
                            dialog.getOnViewClickListener().onViewClick(BindViewHolder.this, view, dialog);
                        }
                    }
                }
            });
        }
        return this;
    }

    public BindViewHolder setText(@IdRes int viewId, CharSequence content) {
        TextView view = getView(viewId);
        view.setText(content);
        return this;
    }

    public BindViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }
}
