package com.fernamuruthi.mkuki.api.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.api.model.Post;
import com.fernamuruthi.mkuki.util.NumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 001590 on 2016-11-25.
 */

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_TRENDING = 100;
    public static final int VIEW_TYPE_NORMAL = 200;
    public static final int COUNT_TRENDING_VIEW_COUNT = 500;

    private Context context;
    private ArrayList<Post> posts;

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        if (getPost(position).getVotesCount() > COUNT_TRENDING_VIEW_COUNT) {
            return VIEW_TYPE_TRENDING;
        } else return VIEW_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TRENDING:
                return new TrendingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_view_trending_hunt, parent, false));
            case VIEW_TYPE_NORMAL:
                return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_hunt, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getPost(position).getVotesCount() > COUNT_TRENDING_VIEW_COUNT) {
            TrendingViewHolder trendingViewHolder = (TrendingViewHolder) holder;
            Glide.with(context).load(getPost(position).getScreenshots().getThreeHundredPxUrl()).fitCenter().centerCrop().placeholder(R.color.colorLightGrey).into(trendingViewHolder.imageViewScreenshot);
            Glide.with(context).load(getPost(position).getThumbnail().getImageUrl()).fitCenter().centerCrop().placeholder(R.color.colorLightGrey).into(trendingViewHolder.imageViewThumbnail);
            trendingViewHolder.textViewName.setText(getPost(position).getName());
            trendingViewHolder.textViewTagline.setText(getPost(position).getTagline());
            trendingViewHolder.textViewVoteCount.setText(NumberUtils.format(getPost(position).getVotesCount()));
            if(getPost(position).getTopics().size()>0){
                trendingViewHolder.textViewTag.setText(getPost(position).getTopics().get(0).getName());
            }
        } else {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            Glide.with(context).load(getPost(position).getThumbnail().getImageUrl()).fitCenter().centerCrop().placeholder(R.color.colorLightGrey).into(normalViewHolder.imageViewThumbnail);
            normalViewHolder.textViewName.setText(getPost(position).getName());
            normalViewHolder.textViewTagline.setText(getPost(position).getTagline());
            normalViewHolder.textViewVoteCount.setText(NumberUtils.format(getPost(position).getVotesCount()));
        }
    }

    private Post getPost(int position) {
        return posts.get(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class TrendingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_screenshot)
        ImageView imageViewScreenshot;
        @BindView(R.id.image_view_thumbnail)
        ImageView imageViewThumbnail;
        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_vote_count)
        TextView textViewVoteCount;
        @BindView(R.id.text_view_tag)
        TextView textViewTag;
        @BindView(R.id.text_view_tagline)
        TextView textViewTagline;
        @BindView(R.id.card_view)
        CardView cardView;

        TrendingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_thumbnail)
        ImageView imageViewThumbnail;
        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_vote_count)
        TextView textViewVoteCount;
        @BindView(R.id.text_view_tagline)
        TextView textViewTagline;
        @BindView(R.id.layout_hunt)
        LinearLayout layoutHunt;

        NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
