
package com.bdcoe.saksham.POJOs;

import com.google.gson.annotations.SerializedName;

public class RegisterResult {

    @SerializedName("result")
    private Long mResult;

    public Long getResult() {
        return mResult;
    }

    public static class Builder {

        private Long mResult;

        public RegisterResult.Builder withResult(Long result) {
            mResult = result;
            return this;
        }

        public RegisterResult build() {
            RegisterResult registerResult = new RegisterResult();
            registerResult.mResult = mResult;
            return registerResult;
        }

    }

}
