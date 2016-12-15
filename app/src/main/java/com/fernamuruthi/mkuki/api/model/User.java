package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-12-06.
 */

public class User {

    private long id;
    private String name;
    private String username;
    private String headline;
    @SerializedName("twitter_username")
    private String twitterUserName;
    @SerializedName("website_url")
    private String websiteUrl;
    @SerializedName("profile_url")
    private String profileUrl;
    @SerializedName("image_url")
    private ImageUrl imageUrl;
    @SerializedName("created_at")
    private String createdAt;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHeadline() {
        return headline;
    }

    public String getTwitterUserName() {
        return twitterUserName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
