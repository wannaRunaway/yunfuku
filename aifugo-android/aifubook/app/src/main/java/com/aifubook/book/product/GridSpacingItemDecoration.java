package com.aifubook.book.product;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author by Damon.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // item position
        int position = parent.getChildAdapterPosition(view);
        // item column
        int column = position % spanCount;

        if (includeEdge) {
            // spacing - column * ((1f / spanCount) * spacing)
            outRect.left = spacing - column * spacing / spanCount;
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount;

            // top edge
            if (position < spanCount) {
                outRect.top = spacing;
            }
            // item bottom
            outRect.bottom = spacing;
        } else {
            // column * ((1f / spanCount) * spacing)
            outRect.left = column * spacing / spanCount;
            // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position >= spanCount) {
                // item top
                outRect.top = spacing;
            }
        }
    }
}
