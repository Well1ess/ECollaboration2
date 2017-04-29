package com.example.a29149.ecollaboration.dto;

/**
 * Created by 张丽华 on 2017/4/17.
 * Description:
 */

public class ApplicationDTO {
    private int id;
    private String type;
    private int projectId;
    private int handlerId;
    private int affectedId;
    private String createdTime;
    private String handleTime;
    private String result;

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ",type=" + type +
                ",projectId=" + projectId +
                ",handlerId=" + handlerId +
                ",affectedId=" + affectedId +
                ",createdTime=" + createdTime +
                ",handleTime=" + handleTime +
                ",result=" + result +
                "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
    }

    public int getAffectedId() {
        return affectedId;
    }

    public void setAffectedId(int affectedId) {
        this.affectedId = affectedId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

