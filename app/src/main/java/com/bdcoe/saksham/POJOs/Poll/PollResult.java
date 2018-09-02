
package com.bdcoe.saksham.POJOs.Poll;

import com.google.gson.annotations.SerializedName;

public class PollResult {

    @SerializedName("list")
    private java.util.List<com.bdcoe.saksham.POJOs.Poll.List> mList;
    @SerializedName("result")
    private Long mResult;

    public java.util.List<com.bdcoe.saksham.POJOs.Poll.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.bdcoe.saksham.POJOs.Poll.List> list) {
        mList = list;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
