
package com.bdcoe.saksham.POJOs.Schedule;

import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("date")
    private String mDate;
    @SerializedName("sport")
    private String mSport;
    @SerializedName("teams")
    private String mTeams;
    @SerializedName("time")
    private String mTime;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

}
