
package com.bdcoe.saksham.POJOs.News;

import com.google.gson.annotations.SerializedName;

public class NewsResult {

    @SerializedName("list")
    private java.util.List<com.bdcoe.saksham.POJOs.News.List> mList;
    @SerializedName("result")
    private Long mResult;

    public java.util.List<com.bdcoe.saksham.POJOs.News.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.bdcoe.saksham.POJOs.News.List> list) {
        mList = list;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
