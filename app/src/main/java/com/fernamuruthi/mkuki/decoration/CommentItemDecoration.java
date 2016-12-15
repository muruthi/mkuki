package com.fernamuruthi.mkuki.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fernamuruthi.mkuki.activity.HuntActivity;
import com.fernamuruthi.mkuki.adapter.CommentRecyclerViewAdapter;
import com.fernamuruthi.mkuki.adapter.PostsAdapter;

/**
 * Created by 001590 on 2016-12-07.
 */

public class CommentItemDecoration extends RecyclerView.ItemDecoration {
    private Resources r;

    public CommentItemDecoration(Context context) {
        this.r = context.getResources();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = getDp(8);

        int position = parent.getChildAdapterPosition(view);
        int viewType = parent.getAdapter().getItemViewType(position);

        if(CommentRecyclerViewAdapter.TYPE_PARENT==viewType) {
            outRect.bottom = getDp(16);
        }
        else if(CommentRecyclerViewAdapter.TYPE_CHILD==viewType){
            outRect.left = getDp(56);
            outRect.bottom = getDp(16);
        }
    }

    private int getDp(int dp){
        return (int) (r.getDisplayMetrics().density * dp);
    }

}
