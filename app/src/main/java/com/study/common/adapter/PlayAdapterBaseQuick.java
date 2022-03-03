package com.study.common.adapter;

import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.study.common.R;
import com.study.common.entity.PlayData;
import com.study.common.widget.KenoPlayItemView;

import java.util.List;

/**
 * Author:zx on 2019/10/2909:08
 */
public class PlayAdapterBaseQuick extends BaseItemDraggableAdapter<PlayData, BaseViewHolder> {

    private int mLayoutType = 0;
    public PlayAdapterBaseQuick(int layoutResId, List<PlayData> data, @Nullable int layoutType) {
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
            }
            holder.addOnClickListener(R.id.ll_container);
            if (holder.getAdapterPosition() > 4) {
                TextView textView = view.getPlayName();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        }
    }
}
