package com.example.a29149.ecollaboration.model.project.task;

/**
 * Created by 张丽华 on 2017/4/19.
 * Description: 用于获取任务列表
 */

public class GetTaskListEvent {
    boolean isSuccess = false;

    public GetTaskListEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
