package com.example.a29149.ecollaboration.dto;

/**
 * Created by 张丽华 on 2017/4/20.
 * Description:
 */

public class ScoreDTO {
    String studentId;
    String typeId;
    String score;

    public ScoreDTO(String studentId, String typeId, String score) {
        this.studentId = studentId;
        this.typeId = typeId;
        this.score = score;
    }

    public ScoreDTO() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
