package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-11-24.
 */

public class Thumbnail {

    private long id;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("image_url")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
