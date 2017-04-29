package com.example.a29149.ecollaboration.model.message.historyapply;

import com.example.a29149.ecollaboration.dto.ApplicationDTO;

import java.util.List;

/**
 * Created by 张丽华 on 2017/4/15.
 * Description: use to update apply list
 */

public class GetApplyEvent {
    boolean isSuccess;
    private List<ApplicationDTO> applicationDTOs;
    private List<List<String>> name;

    public GetApplyEvent(boolean isSuccess, List<ApplicationDTO> applicationDTOs, List<List<String>> name) {
        this.isSuccess = isSuccess;
        this.applicationDTOs = applicationDTOs;
        this.name = name;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<ApplicationDTO> getApplicationDTOs() {
        return applicationDTOs;
    }

    public void setApplicationDTOs(List<ApplicationDTO> applicationDTOs) {
        this.applicationDTOs = applicationDTOs;
    }

    public List<List<String>> getName() {
        return name;
    }

    public void setName(List<List<String>> name) {
        this.name = name;
    }
}
