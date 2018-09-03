
package com.bdcoe.saksham.POJOs.Schedule;

import com.google.gson.annotations.SerializedName;

public class ScheduleResult {

    @SerializedName("list")
    private java.util.List<com.bdcoe.saksham.POJOs.Schedule.List> mList;
    @SerializedName("result")
    private Long mResult;

    public java.util.List<com.bdcoe.saksham.POJOs.Schedule.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.bdcoe.saksham.POJOs.Schedule.List> list) {
        mList = list;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
