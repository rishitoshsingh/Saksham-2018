
package com.bdcoe.saksham.POJOs.Poll;

import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("CE/EI")
    private String mCEEI;
    @SerializedName("CS")
    private String mCS;
    @SerializedName("EC")
    private String mEC;
    @SerializedName("EN")
    private String mEN;
    @SerializedName("IT")
    private String mIT;
    @SerializedName("MCA")
    private String mMCA;
    @SerializedName("ME")
    private String mME;
    @SerializedName("total")
    private String mTotal;

    public String getCEEI() {
        return mCEEI;
    }

    public void setCEEI(String cEEI) {
        mCEEI = cEEI;
    }

    public String getCS() {
        return mCS;
    }

    public void setCS(String cS) {
        mCS = cS;
    }

    public String getEC() {
        return mEC;
    }

    public void setEC(String eC) {
        mEC = eC;
    }

    public String getEN() {
        return mEN;
    }

    public void setEN(String eN) {
        mEN = eN;
    }

    public String getIT() {
        return mIT;
    }

    public void setIT(String iT) {
        mIT = iT;
    }

    public String getMCA() {
        return mMCA;
    }

    public void setMCA(String mCA) {
        mMCA = mCA;
    }

    public String getME() {
        return mME;
    }

    public void setME(String mE) {
        mME = mE;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
