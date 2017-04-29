package com.example.a29149.ecollaboration.model.project.apply;

import com.example.a29149.ecollaboration.dto.ProjectDTO;

import java.util.List;

/**
 * Created by 张丽华 on 2017/4/15.
 * Description:
 */

public class SearchProjectResultListEvent {
    List<ProjectDTO> projectDTOList;

    public SearchProjectResultListEvent(List<ProjectDTO> projectDTOList) {
        this.projectDTOList = projectDTOList;
    }

    public List<ProjectDTO> getProjectDTOList() {
        return projectDTOList;
    }

    public void setProjectDTOList(List<ProjectDTO> projectDTOList) {
        this.projectDTOList = projectDTOList;
    }
}
