package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 001590 on 2016-12-06.
 */

public class Comment {

    private long id;
    private String body;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("parent_comment_id")
    private long parentCommentId;
    private long votes;
    @SerializedName("child_comments_count")
    private long childCommentsCount;
    private String url;
    private User user;
    @SerializedName("child_comments")
    private ArrayList<Comment> childComments;

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getParentCommentId() {
        return parentCommentId;
    }

    public long getVotes() {
        return votes;
    }

    public long getChildCommentsCount() {
        return childCommentsCount;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Comment> getChildComments() {
        return childComments;
    }
}
