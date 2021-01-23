package com.kunminx.linkagelistview.ui.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 2021/1/23
 */
public class SecondaryItemDecoration extends RecyclerView.ItemDecoration {

    private int mRowSpacing;
    private int mColumnSpacing;
    private GridLayoutManager mGridLayoutManager;


    public SecondaryItemDecoration(int rowSpacing, int columnSpacing) {
        this.mRowSpacing = rowSpacing;
        this.mColumnSpacing = columnSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        if (mGridLayoutManager == null) {
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                mGridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            } else {
                return;
            }
        }

        int position = parent.getChildAdapterPosition(view);

        if (mGridLayoutManager.getSpanSizeLookup().getSpanSize(position) == mGridLayoutManager.getSpanCount()) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;

        } else {
            int column = position % mGridLayoutManager.getSpanCount();

            outRect.left = column * mColumnSpacing / mGridLayoutManager.getSpanCount();
            outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mGridLayoutManager.getSpanCount();

            if (position >= mGridLayoutManager.getSpanCount()) {
                outRect.top = mRowSpacing;
            }
        }
    }
}
