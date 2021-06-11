package com.kunminx.linkagelistview.ui.decoration;

import android.graphics.Rect;
import android.util.Log;
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
    private int mLastHeaderPosition;


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
            outRect.bottom = 0;
            mLastHeaderPosition = position;

            Log.d("TAG", "-----getSpanSize " + mGridLayoutManager.getSpanSizeLookup().getSpanSize(position));
        } else {
            outRect.left = mColumnSpacing;
            outRect.top = mRowSpacing;

            int column = Math.abs((position - mLastHeaderPosition) % mGridLayoutManager.getSpanCount());
            if (position > mLastHeaderPosition) {
                outRect.right = column == 0 ? mColumnSpacing : 0;
            } else {
                outRect.right = column == mGridLayoutManager.getSpanCount() - 1 ? mColumnSpacing : 0;
            }
            outRect.bottom = 0;

            Log.d("TAG", "-----column " + column);
            Log.d("TAG", "-----SpanCount " + mGridLayoutManager.getSpanCount());
            Log.d("TAG", "-----outRect.left " + outRect.left);
            Log.d("TAG", "-----outRect.right " + outRect.right);
            Log.d("TAG", "-----outRect.top " + outRect.top);
            Log.d("TAG", "-----");

        }
    }
}
