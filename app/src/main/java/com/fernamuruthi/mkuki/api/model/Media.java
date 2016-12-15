package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-12-06.
 */

public class Media {
    private long id;
    @SerializedName("media_type")
    private String mediaType;
    private int priority;
    private String platform;
    @SerializedName("video_id")
    private String videoId;
    @SerializedName("original_width")
    private int originalWidth;
    @SerializedName("original_height")
    private int originalHeight;
    @SerializedName("image_url")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getPriority() {
        return priority;
    }

    public String getPlatform() {
        return platform;
    }

    public String getVideoId() {
        return videoId;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
