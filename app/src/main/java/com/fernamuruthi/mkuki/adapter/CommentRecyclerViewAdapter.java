package com.fernamuruthi.mkuki.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.api.model.Comment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 001590 on 2016-12-07.
 */

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    public static final int TYPE_PARENT = 100;
    public static final int TYPE_CHILD = 200;

    private Context context;
    private ArrayList<Comment> comments;

    public CommentRecyclerViewAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getItemViewType(int position) {
        if (comments.get(position).getParentCommentId() == 0) {
            return TYPE_PARENT;
        } else {
            return TYPE_CHILD;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(comments.get(position).getUser().getImageUrl().getFourtyPx())
                .fitCenter()
                .placeholder(R.color.colorLightGrey)
                .dontAnimate()
                .into(holder.circleImageViewUser);
        holder.textViewUserName.setText(comments.get(position).getUser().getName());
        holder.textViewUserComment.setText(Html.fromHtml(comments.get(position).getBody()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circle_image_view_user)
        CircleImageView circleImageViewUser;
        @BindView(R.id.text_view_user_name)
        TextView textViewUserName;
        @BindView(R.id.text_view_user_comment)
        TextView textViewUserComment;
        @BindView(R.id.layout_main)
        LinearLayout layoutMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
