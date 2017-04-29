package com.example.a29149.ecollaboration.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by 29149 on 2017/3/2.
 */

public class TeamDTO implements Serializable {


    private Integer id;

    private String teamName;

    private Integer creatorId;

    private String createDate;

    private Integer memberMax;

    private String description;



    private Set<Integer> projectVOIdSet;

    private Set<Integer> studentVOIdSet;

    @Override

    public String toString() {

        return "TeamDTO{" +

                "id=" + id +

                ", teamName='" + teamName +

                ", creatorStudentId=" + creatorId +

                ", createDate='" + createDate +

                ", memberMax=" + memberMax +

                ", description='" + description +

                ", projectVOIdSet=" + projectVOIdSet.size() +

                ", studentVOIdSet=" + studentVOIdSet.size() +

                '}';

    }



    public Integer getId() {

        return id;

    }



    public void setId(Integer id) {

        this.id = id;

    }



    public String getTeamName() {

        return teamName;

    }



    public void setTeamName(String teamName) {

        this.teamName = teamName;

    }



    public Integer getCreatorId() {

        return creatorId;

    }



    public void setCreatorId(Integer creatorId) {

        this.creatorId = creatorId;

    }



    public String getCreateDate() {

        return createDate;

    }



    public void setCreateDate(String createDate) {

        this.createDate = createDate;

    }



    public Integer getMemberMax() {

        return memberMax;

    }



    public void setMemberMax(Integer memberMax) {

        this.memberMax = memberMax;

    }



    public String getDescription() {

        return description;

    }



    public void setDescription(String description) {

        this.description = description;

    }



    public Set<Integer> getProjectVOIdSet() {

        return projectVOIdSet;

    }



    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {

        this.projectVOIdSet = projectVOIdSet;

    }



    public Set<Integer> getStudentVOIdSet() {

        return studentVOIdSet;

    }



    public void setStudentVOIdSet(Set<Integer> studentVOIdSet) {

        this.studentVOIdSet = studentVOIdSet;

    }

}
