
package com.bdcoe.saksham.POJOs.News;

import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("classified_id")
    private String mClassifiedId;
    @SerializedName("desc")
    private String mDesc;
    @SerializedName("image1")
    private String mImage1;
    @SerializedName("image2")
    private String mImage2;
    @SerializedName("image3")
    private String mImage3;
    @SerializedName("image4")
    private String mImage4;
    @SerializedName("sport")
    private String mSport;
    @SerializedName("teams")
    private String mTeams;
    @SerializedName("timestamp")
    private String mTimestamp;

    public String getClassifiedId() {
        return mClassifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        mClassifiedId = classifiedId;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getImage1() {
        return mImage1;
    }

    public void setImage1(String image1) {
        mImage1 = image1;
    }

    public String getImage2() {
        return mImage2;
    }

    public void setImage2(String image2) {
        mImage2 = image2;
    }

    public String getImage3() {
        return mImage3;
    }

    public void setImage3(String image3) {
        mImage3 = image3;
    }

    public String getImage4() {
        return mImage4;
    }

    public void setImage4(String image4) {
        mImage4 = image4;
    }

    public String getSport() {
        return mSport;
    }

    public void setSport(String sport) {
        mSport = sport;
    }

    public String getTeams() {
        return mTeams;
    }

    public void setTeams(String teams) {
        mTeams = teams;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

}
