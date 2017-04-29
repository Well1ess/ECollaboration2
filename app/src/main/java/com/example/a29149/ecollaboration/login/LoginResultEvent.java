package com.example.a29149.ecollaboration.login;

/**
 * Created by 张丽华 on 2017/4/15.
 * Description: dispatch login result to login activity
 */

public class LoginResultEvent {
    boolean success = false;

    public LoginResultEvent(boolean result) {
        this.success = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean result) {
        this.success = result;
    }

    @Override
    public String toString() {
        return "LoginResultEvent{" +
                "success=" + success +
                '}';
    }
}
