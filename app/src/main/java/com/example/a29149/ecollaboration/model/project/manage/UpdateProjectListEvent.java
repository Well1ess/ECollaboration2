package com.example.a29149.ecollaboration.model.project.manage;

/**
 * Created by 张丽华 on 2017/4/18.
 * Description:
 */

public class UpdateProjectListEvent {
    boolean isSuccess;

    public UpdateProjectListEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
