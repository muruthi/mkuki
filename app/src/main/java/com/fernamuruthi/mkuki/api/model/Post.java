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
    private String date;
    @SerializedName("screenshot_url")
    private Screenshots screenshots;
    private Thumbnail thumbnail;
    private ArrayList<Topic> topics;
    @SerializedName("votes_count")
    private long votesCount;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTagline() {
        return tagline;
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
}
