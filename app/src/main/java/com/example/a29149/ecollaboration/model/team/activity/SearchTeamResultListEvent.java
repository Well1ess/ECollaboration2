package com.example.a29149.ecollaboration.model.team.activity;

import com.example.a29149.ecollaboration.dto.TeamDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张丽华 on 2017/4/15.
 * Description:
 */

public class SearchTeamResultListEvent implements Serializable{
    List<TeamDTO> teamDTOs;

    public SearchTeamResultListEvent(List<TeamDTO> teamDTOs) {
        this.teamDTOs = teamDTOs;
    }

    public List<TeamDTO> getTeamDTOs() {
        return teamDTOs;
    }

    public void setTeamDTOs(List<TeamDTO> teamDTOs) {
        this.teamDTOs = teamDTOs;
    }
}
