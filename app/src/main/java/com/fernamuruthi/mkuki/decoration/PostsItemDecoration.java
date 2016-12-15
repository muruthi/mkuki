package com.fernamuruthi.mkuki.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.adapter.PostsAdapter;

/**
 * Created by 001590 on 2016-11-25.
 */

public class PostsItemDecoration extends RecyclerView.ItemDecoration {

    private Resources r;
    private Drawable mDivider;

    public PostsItemDecoration(Context context) {
        r = context.getResources();
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_line);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = getDp(8);

        int finalPosition = parent.getAdapter().getItemCount()-1;
        int position = parent.getChildAdapterPosition(view);
        int viewType = parent.getAdapter().getItemViewType(position);

        if(PostsAdapter.VIEW_TYPE_TRENDING==viewType){
            outRect.left = getDp(8);
            outRect.right = getDp(8);
            outRect.bottom = getDp(8);
        } else if(viewType==0||viewType==PostsAdapter.VIEW_TYPE_PROGRESS_BAR){
            outRect.top = getDp(16);
            outRect.left = getDp(16);
            outRect.right = getDp(16);
            outRect.bottom = getDp(16);
        }else if(position+1<=finalPosition){
            int viewTypeNext = parent.getAdapter().getItemViewType(position+1);
            if(PostsAdapter.VIEW_TYPE_TRENDING==viewTypeNext){
                outRect.bottom = getDp(8);
            }
        }

        if(parent.getChildAdapterPosition(view) == finalPosition && viewType!=PostsAdapter.VIEW_TYPE_PROGRESS_BAR)
            outRect.bottom = getDp(8);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int finalPosition = parent.getAdapter().getItemCount()-1;

        int left = getDp(92);
        int right = parent.getWidth() - getDp(16);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(view);
            int viewType = parent.getAdapter().getItemViewType(position);

            if(viewType==PostsAdapter.VIEW_TYPE_NORMAL&&position!=finalPosition){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

                int top = view.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

        }
    }

    private int getDp(int dp){
        return (int) (r.getDisplayMetrics().density * dp);
    }
}
