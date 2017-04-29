package com.example.a29149.ecollaboration.dto;

/**
 * Created by 张丽华 on 2017/4/19.
 */
public class StudentScoreDTO  {
    private Integer id;
    private Integer typeId;
    private Integer studentId;
    private Integer score;

    public StudentScoreDTO() {
        super();
    }

    @Override
    public String toString() {
        return "StudentScoreDTO{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", studentId=" + studentId +
                ", score=" + score +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
