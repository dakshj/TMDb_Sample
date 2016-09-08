package com.daksh.tmdbsample.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by daksh on 08-Sep-16.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * The minimum amount of items to have below your current scroll position before loading more.
     */
    private int visibleThreshold = 5;

    /**
     * The current offset index of data you have loaded.
     */
    private int currentPageIndex = 0;

    /**
     * The total number of items in the dataset after the last load.
     */
    private int previousTotalItemCount = 0;

    /**
     * The last page index of this endless scrolling dataset.
     */
    private long lastPageIndex = -1;

    private boolean loading = true;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPageIndex = 0;
            previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            //If a lastPageIndex has been set, and is equal to currentPageIndex, then do nothing.
            if (lastPageIndex != -1 && currentPageIndex == lastPageIndex) {
                return;
            }

            currentPageIndex++;
            onLoadMore(currentPageIndex, totalItemCount);
            loading = true;
        }
    }

    /**
     * Defines the process for actually loading more data based on page
     *
     * @param pageIndex
     * @param totalItemCount
     */
    public abstract void onLoadMore(int pageIndex, int totalItemCount);

    /**
     * Indicates that loading for the corresponding page index has failed
     *
     * @param pageIndex
     */
    public void loadingFailed(int pageIndex) {
        if (pageIndex == currentPageIndex) {
            currentPageIndex--;
            loading = false;
        }
    }

    /**
     * Set the last page index of this endless scrolling dataset.
     *
     * @param lastPageIndex
     */
    public void setLastPageIndex(long lastPageIndex) {
        this.lastPageIndex = lastPageIndex;
    }
}
