package com.example.a29149.ecollaboration.util;

/**
 * Created by 张丽华 on 2017/3/8.
 */

public class URL {

    private final static String RE = "http://";
    //public final static String IP="10.53.230.74";
    //public final static String PORT=":8080";
    private final static String IP = "123.206.102.231";
    private final static String PORT = ":80";
    private final static String WEBAPP = "";

    private final static String QuitAction = "/logOut.action?";

    private final static String LoginAction = "/logIn.action?";
    private final static String EditPhotoAction = "/appUploadPhoto.action?";
    private final static String EditSelfInfoAction = "/appUploadPhoto.action?";

    private final static String CreateTeamAction = "/createTeam.action?";
    private final static String GetMyJoinTeamsAction = "/getMyJoinTeams.action?";
    private final static String GetMyJoinTeamInfo = "/getTeamInfo.action?";
    private final static String EditTeamAction = "/modifyTeamInfo?";
    private final static String SearchTeamKeyWord = "/searchTeam.action?";
    private final static String ApplyTeam = "/applyJoinTeam?";
    private final static String GetTeamApply = "/getApplication.action?";

    private final static String SearchProjectUseKeyWord = "/appSearchProjectAction.action?";
    private final static String CreateSchProActionByTeacher = "/addProjectAction.action?";
    private final static String CreateSchProActionByStudent = "/addProjectAction.action?";
    private final static String getMySchProList = "/getMyProjectVOList.action?";
    private final static String ApplyProject = "/applyProject.action?";
    private final static String modifyProjectInfoAction = "/modifyProjectInfoAction.action?";

    private final static String appAcceptJoinTeamApply = "/acceptJoinApplication.action?";
    private final static String appRefuseJoinTeamApply = "/refuseJoinApplication.action?";
    private final static String appAcceptJoinProjectApply = "/acceptApplication.action?";
    private final static String appRefuseJoinProjectApply = "/refuseApplication.action?";

    private final static String GetMyTeamsTask = "/getMyTask.action?";
    private final static String createTask = "/createTask.action?";
    private final static String getMyTaskByTeacher = "/getMyTaskByTeacher.action?";

    private final static String getTeamsByTeacher = "/appGetTeamsByTeacher.action?";
    private final static String getTeamsBySchool = "/getTeamsByProject.action?";
    private final static String getAccess =  "/getAccess.action?";
    private final static String exeEvaluation = "/getAccess.action?";

    private final static String deleteTeam = "deleteTeam";
    private final static String deleteProject = "deleteProjectAction";


    public static String getQuitActionURL() {
        return RE + IP + PORT + WEBAPP + QuitAction;
    }

    /**
     * @param username 用户名
     * @param password 密码
     * @return url
     */
    public static String getLoginURL(String username, String password) {
        return RE + IP + PORT + WEBAPP + LoginAction + "userName=" + username + "&passWord=" + password;
    }

    /**
     * @param location 位置
     * @return
     */
    public static String getPhotoURL(String location) {
        return RE + IP + PORT + WEBAPP + "/" + location;
    }

    /**
     * @param id               用户id
     * @param userImageContent 其他信息
     * @return
     */
    public static String getEditPhotoAction(String id, String userImageContent) {
        return RE + IP + PORT + WEBAPP + EditPhotoAction + "id=" + id + "&userImageContent=" + userImageContent;
    }

    /**
     * @param id      用户id
     * @param email   用户email
     * @param numbers 用户联系方式
     * @param sex     用户性别
     * @param school  用户学校
     * @param achieve 用户参加过的项目
     * @return
     */
    public static String getEditUserInfoActionURL(String id, String email, String numbers, String sex, String school, String achieve) {
        return RE + IP + PORT + WEBAPP + EditSelfInfoAction + "id=" + id + "&email=" + email + "&numbers=" + numbers + "&sex=" + sex + "&school=" + school + "&achieve=" + achieve;
    }

    /**
     * @param teamName    团队名称
     * @param description 团队描述
     * @param memberMax   人数上限
     * @return
     */
    public static String getCreateTeamURL(String teamName, String description, String memberMax) {

        return RE + IP + PORT + WEBAPP + CreateTeamAction + "teamName=" + java.net.URLEncoder.encode(teamName) + "&description=" + java.net.URLEncoder.encode(description) + "&memberMax=" + java.net.URLEncoder.encode(memberMax);

    }

    /**
     * @return
     */
    public static String getMyJoinTeamsAction() {
        return RE + IP + PORT + WEBAPP + GetMyJoinTeamsAction;
    }

    /**
     * @param teamId 团队ID
     * @return
     */
    public static String getGetMyJoinTeamsInfoAction(String teamId) {
        return RE + IP + PORT + WEBAPP + GetMyJoinTeamInfo + "teamId=" + teamId;
    }

    /**
     * @param id          项目ID
     * @param teamName    团队名称
     * @param description 团队描述
     * @param memberMax   人数上限
     * @return
     */
    public static String getEditTeamAction(String id, String teamName, String description, String memberMax) {

        return RE + IP + PORT + WEBAPP + EditTeamAction + "id=" + id + "&teamName=" + java.net.URLEncoder.encode(teamName) + "&description=" + java.net.URLEncoder.encode(description) + "&memberMax=" + java.net.URLEncoder.encode(memberMax);

    }

    /**
     * @param teamSearchInfo 关键字
     * @return
     */
    public static String getSearchTeamKeyWord(String teamSearchInfo) {
        return RE + IP + PORT + WEBAPP + SearchTeamKeyWord + "teamSearchInfo=" + java.net.URLEncoder.encode(teamSearchInfo);
    }

    /**
     * @param teamId 目标团队的id
     * @return
     */
    public static String getApplyTeam(String teamId) {
        return RE + IP + PORT + WEBAPP + ApplyTeam + "teamId=" + teamId;
    }

    public static String getApplyWhichApplyMyTeam() {
        return RE + IP + PORT + WEBAPP + GetTeamApply;
    }

    /**
     * @param role            根据role判断调用哪个action
     * @param name            项目名
     * @param applyBeforeDate 申请项目截止时间
     * @param finishDate      项目截止日期
     * @param survivalDate    项目有效期
     * @param teamMax         最大团队数
     * @param memberMax       团队人员数
     * @param keyWord         关键字
     * @param info            描述
     * @param requirement     需求
     * @param gain            获取技能
     * @return
     */
    public static String getCreateSchoolProject(String role, String name, String applyBeforeDate,
                                                String finishDate, String survivalDate, String teamMax, String memberMax,
                                                String keyWord, String info, String requirement, String gain) {

        String action = CreateSchProActionByTeacher;
        if (!role.equals("student")) {
            action = CreateSchProActionByStudent;
        }
        return RE + IP + PORT + WEBAPP + action +
                "name=" + name + "&applyBeforeDate=" + applyBeforeDate +
                "&finishDate=" + finishDate + "&survivalDate=" + survivalDate +
                "&teamMax=" + teamMax + "&memberMax=" + memberMax +
                "&keyWord=" + keyWord + "&info=" + info + "&requirement=" + requirement + "&gain=" + gain;
    }

    /**
     * @param teamId    队伍id
     * @param projectId 目标项目id
     * @return
     */
    public static String getApplyProjectURL(String teamId, String projectId) {
        return RE + IP + PORT + WEBAPP + ApplyProject + "teamId=" + teamId + "&projectId=" + projectId;
    }

    public static String getModifyProjectInfoAction(String id, String name, String applyBeforeDate,
                                                    String finishDate, String survivalDate, String teamMax, String memberMax,
                                                    String keyWord, String info, String requirement, String gain, String priority) {
        return RE + IP + PORT + WEBAPP + modifyProjectInfoAction +
                "id=" + id +
                "&name=" + name + "&applyBeforeDate=" +
                java.net.URLEncoder.encode(applyBeforeDate) +
                "&finishDate=" +
                java.net.URLEncoder.encode(finishDate) +
                "&survivalDate=" +
                java.net.URLEncoder.encode(survivalDate) +
                "&teamMax=" + teamMax + "&memberMax=" + memberMax +
                "&keyWord=" + keyWord + "&info=" + info + "&requirement=" + requirement + "&gain=" + gain + "&priority=" + priority;
    }

    public static String getGetMyProjectListURL() {
        return RE + IP + PORT + WEBAPP + getMySchProList;
    }


    public static String getSearchProjectUseKeyWordURL(String keyWord) {
        return RE + IP + PORT + WEBAPP + SearchProjectUseKeyWord + "keyWord=" + keyWord;
    }

    public static String getAppAcceptJoinTeamApplyURL(String applicationId) {
        return RE + IP + PORT + WEBAPP + appAcceptJoinTeamApply + "applicationId=" + applicationId;
    }

    public static String getAppRefuseJoinTeamApply(String applicationId) {
        return RE + IP + PORT + WEBAPP + appRefuseJoinTeamApply + "applicationId=" + applicationId;
    }

    public static String getAppAcceptJoinProjectApply(String applicationId) {
        return RE + IP + PORT + WEBAPP + appAcceptJoinProjectApply + "applicationId=" + applicationId;
    }

    public static String getAppRefuseJoinProjectApply(String applicationId) {
        return RE + IP + PORT + WEBAPP + appRefuseJoinProjectApply + "applicationId=" + applicationId;
    }

    public static String getMyTeamsProjectsTask(String projectId) {
        return RE + IP + PORT + WEBAPP + GetMyTeamsTask + "projectId=" + projectId;
    }

    public static String getCreateTaskURL(String projectId,
                                          String content,
                                          String beginDate,
                                          String targetDate) {
        return RE + IP + PORT + WEBAPP + createTask +
                "projectId=" + projectId +
                "&content=" + content +
                "&beginDate=" + beginDate +
                "&targetDate=" + targetDate;
    }

    public static String getMyTaskByTeacher()
    {
        return RE + IP + PORT + WEBAPP +getMyTaskByTeacher;
    }

    public static String getTeamsByTeacher()
    {
        return RE + IP + PORT + WEBAPP + getTeamsByTeacher;
    }

    public static String getTeamsBySchool(String projectId)
    {
        return RE + IP + PORT + WEBAPP + getTeamsBySchool+"&projectId=" +projectId;
    }

    public static String getAccess(String projectId, String teamId)
    {
        return RE + IP + PORT + WEBAPP + getAccess+"projectId=" +projectId +"&teamId="+teamId;
    }

    public static String getDeleteTeam(String teamId)
    {
        return RE + IP + PORT + WEBAPP + deleteTeam +"&teamId="+teamId;
    }
    public static String getDeleteProject(String projectId)
    {
        return RE + IP + PORT + WEBAPP + deleteProject +"&projectId="+projectId;
    }
}
