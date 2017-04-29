package com.example.a29149.ecollaboration.dto;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class Apply {
    private String flag;
    private String title;//申请的什么，若申请团队则团队名，若申请项目则项目名
    private String content;//输入的信息
    private List<String> name;//项目名
    private String applyMan;
    //团队名
    //处理人
    //申请人

    private ApplicationDTO applicationDTO;

    public Apply() {
    }

    public Apply(ApplicationDTO applicationDTO, List<String> name) {
        this.applicationDTO = applicationDTO;
        this.name = name;
        setFlag(applicationDTO.getType().equals("team") ? "团队" : "项目");
        this.content = setContent(name);
        title = applicationDTO.getType().equals("team") ? "团队申请" : "项目申请";
        this.applyMan = name.get(3);
    }

    public String setContent(List<String> name) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(name.get(3));
        stringBuilder.append("申请您的");
        if (!name.get(0).equals("0"))
            stringBuilder.append("项目:" + name.get(0));
        else
            stringBuilder.append("团队:" + name.get(1));

        return stringBuilder.toString();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public ApplicationDTO getApplicationDTO() {
        return applicationDTO;
    }

    public void setApplicationDTO(ApplicationDTO applicationDTO) {
        this.applicationDTO = applicationDTO;
    }

    public String getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }
}
