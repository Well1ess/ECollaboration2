package com.example.a29149.ecollaboration.mainactivity;

/**
 * Created by 张丽华 on 2017/4/15.
 * Description: dispatch http result to main activity
 */

public class HttpResponseEvent {
    boolean isSuccess;
    String msg;

    public HttpResponseEvent(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
