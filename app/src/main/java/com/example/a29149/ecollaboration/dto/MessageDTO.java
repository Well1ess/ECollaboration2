package com.example.a29149.ecollaboration.dto;

/**
 * Created by 29149 on 2017/3/31.
 */

public class MessageDTO {

    private String id;
    private String project;
    private String team;
    private String creator;
    private String content;
    private String time;

    public MessageDTO() {
    }

    public MessageDTO(String creator, String content, String time, String id) {
        this.creator = creator;
        this.content = content;
        this.time = time;
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
