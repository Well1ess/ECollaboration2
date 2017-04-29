package com.example.a29149.ecollaboration.model.team;

/**
 * Created by 张丽华 on 2017/4/19.
 * Description:
 */

public class GetTeamInfoEvent {
    boolean isSuccess = false;

    public GetTeamInfoEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
