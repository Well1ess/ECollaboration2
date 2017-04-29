package com.example.a29149.ecollaboration.dto;

import java.util.Set;

/**
 * Created by 29149 on 2017/3/2.
 */

public class TeacherDTO extends UserDTO {

    private String homePageUrl;

    private Integer needStudentsFlag;

    private Set<Integer> projectVOIdSet;


    @Override

    public String toString(){

        return  "TeacherDTO{" + super.toString() +

                ",homePageUrl='" + homePageUrl + '\'' +

                ", needStudentsFlag=" + needStudentsFlag +

                ", projectVOIdSet=" + projectVOIdSet.size() +

                '}';

    }





    public String getHomePageUrl() {

        return homePageUrl;

    }



    public void setHomePageUrl(String homePageUrl) {

        this.homePageUrl = homePageUrl;

    }



    public Integer getNeedStudentsFlag() {

        return needStudentsFlag;

    }



    public void setNeedStudentsFlag(Integer needStudentsFlag) {

        this.needStudentsFlag = needStudentsFlag;

    }



    public Set<Integer> getProjectVOIdSet() {

        return projectVOIdSet;

    }



    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {

        this.projectVOIdSet = projectVOIdSet;

    }

}
