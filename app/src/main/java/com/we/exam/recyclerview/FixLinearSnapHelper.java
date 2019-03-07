package com.we.exam.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhou on 2017/8/2.
 */

public class FixLinearSnapHelper extends LinearSnapHelper {

    private OrientationHelper mVerticalHelper;

    private OrientationHelper mHorizontalHelper;

    private RecyclerView mRecyclerView;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        this.mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        final View currentView = findSnapView(layoutManager);
        if(targetPos != RecyclerView.NO_POSITION && currentView != null){
            int currentPosition = layoutManager.getPosition(currentView);
            int first = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
            int last = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
            currentPosition = targetPos < currentPosition ? last : (targetPos > currentPosition ? first : currentPosition);
            targetPos = targetPos < currentPosition ? currentPosition - 1 : (targetPos > currentPosition ? currentPosition + 1 : currentPosition);
        }
        return targetPos;
    }

    private int distanceToCenter(View targetView, OrientationHelper helper) {
        //如果已经滚动到尽头 并且判断是否是第一个item或者是最后一个，直接返回0，不用多余的滚动了
        if ((helper.getDecoratedStart(targetView) == 0 && mRecyclerView.getChildAdapterPosition(targetView) == 0)
                || (helper.getDecoratedEnd(targetView) == helper.getEndAfterPadding()
                && mRecyclerView.getChildAdapterPosition(targetView) == mRecyclerView.getAdapter().getItemCount() - 1) )
            return 0;

        int viewCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedEnd(targetView) - helper.getDecoratedStart(targetView))/2;
        int correctCenter = (helper.getEndAfterPadding() - helper.getStartAfterPadding())/2;
        return viewCenter - correctCenter;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

}
