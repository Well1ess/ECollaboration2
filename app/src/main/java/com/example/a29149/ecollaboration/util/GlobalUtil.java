package com.example.a29149.ecollaboration.util;

import android.graphics.Bitmap;

import com.example.a29149.ecollaboration.dto.MessageDTO;
import com.example.a29149.ecollaboration.dto.ProjectAccessTypeDTO;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.dto.StudentDTO;
import com.example.a29149.ecollaboration.dto.StudentScoreDTO;
import com.example.a29149.ecollaboration.dto.TaskDTO;
import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29149 on 2017/3/8.
 */

public class GlobalUtil {

    private volatile static GlobalUtil globalUtil;

    private String sessionId = "";
    private String role;

    private UserDTO userDTO;
    private Object teacherDTO;
    private Object studentDTO;

    private Bitmap mHead;

    //my join teams
    private List<TeamDTO> teamDTOs;
    private List<ProjectDTO> projectDTOs;

    private List<TaskDTO> myExeTask;

    //我团队下执行的项目
    private List<ProjectDTO> myExeProjectDTOs;

    //the team list whom exe my project
    private List<TeamDTO> mManagerTeams;
    private List<ProjectDTO> projectDTOList;

    private List<MessageDTO> messageDTOList;
    private List<StudentDTO> studentsBeans;
    private List<ProjectAccessTypeDTO> projectAccessTypeDTOs;
    private List<List<StudentScoreDTO>> studentsScoreDTO;

    private List<TaskDTO> myTask;

    private int indexChild = 0;

    private GlobalUtil() {

    }

    public static GlobalUtil getInstance() {
        synchronized (GlobalUtil.class) {
            if (globalUtil == null) {
                globalUtil = new GlobalUtil();
            }
        }
        return globalUtil;
    }

    public List<StudentDTO> getStudentsBeans() {
        return studentsBeans;
    }

    public void setStudentsBeans(List<StudentDTO> studentsBeans) {
        this.studentsBeans = studentsBeans;
    }

    public List<ProjectAccessTypeDTO> getProjectAccessTypeDTOs() {
        return projectAccessTypeDTOs;
    }

    public void setProjectAccessTypeDTOs(List<ProjectAccessTypeDTO> projectAccessTypeDTOs) {
        this.projectAccessTypeDTOs = projectAccessTypeDTOs;
    }

    public List<List<StudentScoreDTO>> getStudentsScoreDTO() {
        return studentsScoreDTO;
    }

    public void setStudentsScoreDTO(List<List<StudentScoreDTO>> studentsScoreDTO) {
        this.studentsScoreDTO = studentsScoreDTO;
    }

    public List<TaskDTO> getMyTask() {
        if (myTask == null)
            myTask = new ArrayList<>();
        return myTask;
    }

    public void setMyTask(List<TaskDTO> myTask) {
        this.myTask = myTask;
    }

    public void addMyTask(TaskDTO taskDTO)
    {
        if (myTask == null)
            myTask = new ArrayList<>();
        myTask.add(taskDTO);
    }

    public List<TaskDTO> getMyExeTask() {
        return myExeTask;
    }

    public void setMyExeTask(List<TaskDTO> myExeTask) {
        this.myExeTask = myExeTask;
    }

    public List<ProjectDTO> getMyExeProjectDTOs() {
        return myExeProjectDTOs;
    }

    public void setMyExeProjectDTOs(List<ProjectDTO> myExeProjectDTOs) {
        this.myExeProjectDTOs = myExeProjectDTOs;
    }

    public String getSessionId() {
        if (sessionId == null) {
            return "";
        }
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Bitmap getmHead() {
        return mHead;
    }

    public void setmHead(Bitmap mHead) {
        this.mHead = mHead;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void addProjectToProjectDTOList(ProjectDTO projectDTO) {
        if (this.projectDTOList == null)
            this.projectDTOList = new ArrayList<>();
        this.projectDTOList.add(projectDTO);
    }

    public int getIndexChild() {
        return indexChild;
    }

    public void setIndexChild(int indexChild) {
        this.indexChild = indexChild;
    }

    /**
     * UserDTO的设置器
     *
     * @return
     */
    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * TeacherDTO的设置
     *
     * @return
     */
    public Object getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(Object teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    /**
     * StudentDTO的设置
     *
     * @return
     */
    public Object getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(Object studentDTO) {
        this.studentDTO = studentDTO;
    }

    /**
     * teamDTO的设置
     *
     * @return
     */
    public List<TeamDTO> getTeamDTO() {
        if (teamDTOs == null) {
            teamDTOs = new ArrayList<>();

        }
        return teamDTOs;
    }

    public void setTeamDTO(List<TeamDTO> teamDTO) {
        this.teamDTOs = teamDTO;
    }

    public boolean addTeam(TeamDTO team) {
        if (teamDTOs == null) {
            teamDTOs = new ArrayList<>();
        }
        return teamDTOs.add(team);
    }

    public List<ProjectDTO> getProjectDTOs() {
        if (projectDTOs == null) {
            projectDTOs = new ArrayList<>();
        }
        return projectDTOs;
    }

    public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
        this.projectDTOs = projectDTOs;
    }

    public List<TeamDTO> getmManagerTeams() {
        if (mManagerTeams == null) {
            mManagerTeams = new ArrayList<>();
        }
        return mManagerTeams;
    }

    public void setmManagerTeams(List<TeamDTO> mManagerTeams) {
        this.mManagerTeams = mManagerTeams;
    }


    public List<MessageDTO> getMessageDTOList() {
        return messageDTOList;
    }

    public void setMessageDTOList(List<MessageDTO> messageDTOList) {
        this.messageDTOList = messageDTOList;
    }
}
