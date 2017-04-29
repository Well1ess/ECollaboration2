package com.example.a29149.ecollaboration.model.evaluation;

/**
 * Created by 张丽华 on 2017/4/19.
 * Description:
 */

public class GetTeachersMTeamEvent {
    boolean isSuccess;

    public GetTeachersMTeamEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
