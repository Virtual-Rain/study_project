package com.study.common.adapter;

import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.study.common.R;
import com.study.common.entity.PlayData;
import com.study.common.widget.KenoPlayItemView;

import java.util.List;

/**
 * Author:zx on 2019/10/2414:14
 */
public class PlayAdapter extends BaseItemTouchAdapter<PlayData, BaseViewHolder>{

    private int mLayoutType = 0;

    public PlayAdapter(int layoutResId, @Nullable List<PlayData> data, @Nullable int layoutType) {
        super(layoutResId, data);
        mLayoutType = layoutType;
    }

    @Override
    protected void convert(BaseViewHolder holder, PlayData item) {
        KenoPlayItemView view = holder.getView(R.id.kbv_item);
        if(view!=null){
            view.setPlayName(item.getPlayName());
            view.setOdds(item.getPlayCode());
            if(mLayoutType==1){
                view.setConstraintLayout();
            }else{
                view.setLayoutParams();
                view.setConstraintLayout();
            }
            holder.addOnClickListener(R.id.ll_container);
            if (holder.getAdapterPosition() > 4) {
                TextView textView = view.getPlayName();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        }
    }

}
