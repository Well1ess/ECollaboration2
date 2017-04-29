package com.example.a29149.ecollaboration.dto;

/**
 * Created by 张丽华 on 2017/4/19.
 */

public class ProjectAccessTypeDTO {
    private Integer id;
    private Integer projectId;
    private String type;

    public ProjectAccessTypeDTO() {

    }

    @Override
    public String toString() {
        return "ProjectAccessTypeDTO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
