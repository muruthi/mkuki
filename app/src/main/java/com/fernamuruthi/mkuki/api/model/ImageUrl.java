package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-12-06.
 */
public class ImageUrl {
    @SerializedName("40px")
    private String fourtyPx;
    @SerializedName("48px")
    private String fourtyEightPx;
    @SerializedName("60px")
    private String sixtyPx;
    @SerializedName("80px")
    private String eightyPx;
    @SerializedName("96px")
    private String ninetySixPx;
    @SerializedName("100px")
    private String oneHundredPx;
    private String original;

    public String getFourtyPx() {
        return fourtyPx;
    }

    public String getFourtyEightPx() {
        return fourtyEightPx;
    }

    public String getSixtyPx() {
        return sixtyPx;
    }

    public String getEightyPx() {
        return eightyPx;
    }

    public String getNinetySixPx() {
        return ninetySixPx;
    }

    public String getOneHundredPx() {
        return oneHundredPx;
    }

    public String getOriginal() {
        return original;
    }
}
