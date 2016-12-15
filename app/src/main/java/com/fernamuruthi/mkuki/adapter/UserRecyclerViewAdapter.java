package com.fernamuruthi.mkuki.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.api.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 001590 on 2016-12-06.
 */

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> users;

    public UserRecyclerViewAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(users.get(position).getImageUrl().getFourtyPx()).fitCenter().placeholder(R.color.colorLightGrey).dontAnimate().into(holder.circleImageViewUser);
        holder.textViewUser.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circle_image_view_user)
        CircleImageView circleImageViewUser;
        @BindView(R.id.text_view_user)
        TextView textViewUser;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
