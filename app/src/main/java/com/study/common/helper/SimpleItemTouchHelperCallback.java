package com.study.common.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.study.common.adapter.BaseItemTouchAdapter;

import java.util.List;

/**
 * Author:zx on 2019/10/2514:30
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private BaseItemTouchAdapter mAdapter;
    private boolean isDrag = true;
    private boolean isSwipe = false;

    public SimpleItemTouchHelperCallback(BaseItemTouchAdapter adapter) {
        mAdapter = adapter;
    }

    public void setDrag(boolean drag) {
        isDrag = drag;
    }

    public void setSwipe(boolean swipe) {
        isSwipe = swipe;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return isDrag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipe;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags = ItemTouchHelper.LEFT;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        int flag = 0;
        if (isDrag && isSwipe) {
            flag = makeMovementFlags(dragFlags, swipeFlags);
        } else if (isDrag) {
            flag = makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags);
        } else if (isSwipe) {
            flag = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags);
        }
        return flag;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(source, target);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            mAdapter.onItemSelect(viewHolder);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!recyclerView.isComputingLayout()) {
            mAdapter.onItemClear(viewHolder);
        }
    }

    @Override
    public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
        int right = curX + selected.itemView.getWidth();
        int halfHeight = selected.itemView.getHeight() / 2;
        int bottom = curY + halfHeight * 2;
        RecyclerView.ViewHolder winner = null;
        int winnerScore = -1;
        final int dx = curX - selected.itemView.getLeft();
        final int dy = curY - selected.itemView.getTop();
        final int targetsSize = dropTargets.size();
        for (int i = 0; i < targetsSize; i++) {
            final RecyclerView.ViewHolder target = dropTargets.get(i);
            if (dx > 0) {
                int diff = target.itemView.getRight() - right;
                if (diff < 0 && target.itemView.getRight() > selected.itemView.getRight()) {
                    final int score = Math.abs(diff);
                    if (score > winnerScore) {
                        winnerScore = score;
                        winner = target;
                    }
                }
            }
            if (dx < 0) {
                int diff = target.itemView.getLeft() - curX;
                if (diff > 0 && target.itemView.getLeft() < selected.itemView.getLeft()) {
                    final int score = Math.abs(diff);
                    if (score > winnerScore) {
                        winnerScore = score;
                        winner = target;
                    }
                }
            }
            if (dy < 0) {
                int diff = target.itemView.getTop() - curY + halfHeight;
                if (diff > 0 && target.itemView.getTop() < selected.itemView.getTop()) {
                    final int score = Math.abs(diff);
                    if (score > winnerScore) {
                        winnerScore = score;
                        winner = target;
                    }
                }
            }

            if (dy > 0) {
                int diff = target.itemView.getBottom() - bottom - halfHeight;
                if (diff < 0 && target.itemView.getBottom() > selected.itemView.getBottom()) {
                    final int score = Math.abs(diff);
                    if (score > winnerScore) {
                        winnerScore = score;
                        winner = target;
                    }
                }
            }
        }
        return winner;
    }
}
