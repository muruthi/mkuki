package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-12-06.
 */

public class InstallLink {
    private long id;
    @SerializedName("post_id")
    private long postId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("primary_link")
    private boolean primaryLink;
    @SerializedName("redirect_url")
    private String redirectUrl;
    private String platform;

    public long getId() {
        return id;
    }

    public long getPostId() {
        return postId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isPrimaryLink() {
        return primaryLink;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getPlatform() {
        return platform;
    }
}
