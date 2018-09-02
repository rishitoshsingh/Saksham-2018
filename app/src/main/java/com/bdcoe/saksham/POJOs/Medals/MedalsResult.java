
package com.bdcoe.saksham.POJOs.Medals;

import com.google.gson.annotations.SerializedName;

public class MedalsResult {

    @SerializedName("list")
    private java.util.List<com.bdcoe.saksham.POJOs.Medals.List> mList;
    @SerializedName("result")
    private Long mResult;

    public java.util.List<com.bdcoe.saksham.POJOs.Medals.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.bdcoe.saksham.POJOs.Medals.List> list) {
        mList = list;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
