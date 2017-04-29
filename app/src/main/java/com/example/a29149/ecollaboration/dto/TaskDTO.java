package com.example.a29149.ecollaboration.dto;

import java.util.Set;

/**
 * Created by 29149 on 2017/3/20.
 */

public class TaskDTO {

    private String time;

    private Integer id;
    private String title;
    private String content;
    private Integer creatorTeacherVOId;
    private String createDate;
    private String modifyDate;
    private String beginDate;
    private String targetDate;
    private Set<Integer> projectVOIdSet;

    private Integer projectId;

    @Override
    public String toString() {
        return "TaskVO{" +
                "id=" + id +
                ", title='" + title +
                ", content='" + content +
                ", creatorTeacherVOId=" + creatorTeacherVOId +
                ", createDate='" + createDate +
                ", modifyDate='" + modifyDate +
                ", beginDate='" + beginDate +
                ", targetDate='" + targetDate +
                ", projectVOIdSet=" + projectVOIdSet.size() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreatorTeacherVOId() {
        return creatorTeacherVOId;
    }

    public void setCreatorTeacherVOId(Integer creatorTeacherVOId) {
        this.creatorTeacherVOId = creatorTeacherVOId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Set<Integer> getProjectVOIdSet() {
        return projectVOIdSet;
    }

    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {
        this.projectVOIdSet = projectVOIdSet;
    }


    private String changeTime(String time)
    {
        int index=time.indexOf("月");
        StringBuffer stringBuffer=new StringBuffer(time);
        stringBuffer.insert(index+1, "\n");

        return stringBuffer.toString();
    }

    private String getAllTime(String time, String endTime)
    {
        return time+"\n"+endTime;
    }

    public String getTime() {
        return time = getAllTime(beginDate, targetDate);
    }

    public void setTime(String time) {
        this.time = time;
    }
}
