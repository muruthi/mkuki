package com.fernamuruthi.mkuki.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 001590 on 2016-11-24.
 */

public class Screenshots {

    @SerializedName("300px")
    private String threeHundredPxUrl;
    @SerializedName("850px")
    private String eightHundredPxUrl;

    public String getThreeHundredPxUrl() {
        return threeHundredPxUrl;
    }

    public String getEightHundredPxUrl() {
        return eightHundredPxUrl;
    }
}
