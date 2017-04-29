package com.example.a29149.ecollaboration.mainactivity;

/**
 * Created by 张丽华 on 2017/4/16.
 * Description:
 */

public class EditInfoEvent {
    boolean isSuccess;

    public EditInfoEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
