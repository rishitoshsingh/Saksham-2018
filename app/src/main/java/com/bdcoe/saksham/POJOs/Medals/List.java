
package com.bdcoe.saksham.POJOs.Medals;

import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("branch")
    private String mBranch;
    @SerializedName("bronze")
    private String mBronze;
    @SerializedName("gold")
    private String mGold;
    @SerializedName("silver")
    private String mSilver;
    @SerializedName("total")
    private String mTotal;

    public String getBranch() {
        return mBranch;
    }

    public void setBranch(String branch) {
        mBranch = branch;
    }

    public String getBronze() {
        return mBronze;
    }

    public void setBronze(String bronze) {
        mBronze = bronze;
    }

    public String getGold() {
        return mGold;
    }

    public void setGold(String gold) {
        mGold = gold;
    }

    public String getSilver() {
        return mSilver;
    }

    public void setSilver(String silver) {
        mSilver = silver;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
