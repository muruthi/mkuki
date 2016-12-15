package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 001590 on 2016-11-24.
 */

public class Post {

    private long id;
    private String name;
    private String tagline;
    private String day;
    @SerializedName("screenshot_url")
    private Screenshots screenshots;
    private Thumbnail thumbnail;
    private ArrayList<Topic> topics;
    @SerializedName("votes_count")
    private long votesCount;
    @SerializedName("comments_count")
    private long commentsCount;
    @SerializedName("discussion_url")
    private String discussionUrl;
    @SerializedName("redirect_url")
    private String redirectUrl;
    private User user;
    private ArrayList<User> makers;
    private ArrayList<Comment> comments;
    @SerializedName("related_posts")
    private ArrayList<Post> relatedPosts;
    @SerializedName("install_links")
    private ArrayList<InstallLink> installLinks;
    private ArrayList<Media> media;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getDay() {
        return day;
    }

    public Screenshots getScreenshots() {
        return screenshots;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public long getVotesCount() {
        return votesCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public String getDiscussionUrl() {
        return discussionUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getMakers() {
        return makers;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Post> getRelatedPosts() {
        return relatedPosts;
    }

    public ArrayList<InstallLink> getInstallLinks() {
        return installLinks;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }
}
