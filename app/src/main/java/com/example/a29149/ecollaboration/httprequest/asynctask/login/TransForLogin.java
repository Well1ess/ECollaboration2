package com.example.a29149.ecollaboration.httprequest.asynctask.login;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.dto.StudentDTO;
import com.example.a29149.ecollaboration.dto.TeacherDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransForGetSchoolProject;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForGetMyJoinTeams;
import com.example.a29149.ecollaboration.login.LoginResultEvent;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.URL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/18 0018.
 */

public class TransForLogin extends AsyncTask<String, Integer, String> {

    public TransForLogin(Activity loginActivity) {
        super();
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getLoginURL(params[0], params[1]));
            con = (HttpURLConnection) url.openConnection();
            log.d(this, URL.getLoginURL(params[0], params[1]));
            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);

            con.setRequestMethod("GET");
            con.setRequestProperty("contentType", "GBK");


            // 获得服务端的返回数据
            InputStreamReader read = new InputStreamReader(con.getInputStream());
            if (con.getHeaderField("Set-Cookie") != null) {
                String session = con.getHeaderField("Set-Cookie");
                GlobalUtil.getInstance().setSessionId(session.substring(0, session.indexOf(";")));
            }

            log.d(this, GlobalUtil.getInstance().getSessionId());

            reader = new BufferedReader(read);
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        log.d(this, result);

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String resultFlag = jsonObject.getString("result");

                log.d(this, result);

                if (resultFlag.equals("success")) {
                    if (jsonObject.getString("role").equals("teacher")) {
                        GlobalUtil.getInstance().setRole("teacher");

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                        }.getType();
                        TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("teacherBean"), type);
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                        GlobalUtil.getInstance().setUserDTO(teacherDTO);
                    } else {
                        GlobalUtil.getInstance().setRole("student");

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                        }.getType();
                        StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentBean"), type);
                        GlobalUtil.getInstance().setStudentDTO(studentDTO);
                        GlobalUtil.getInstance().setUserDTO(studentDTO);

                        TransForGetMyJoinTeams joinTeams = new TransForGetMyJoinTeams();
                        joinTeams.execute();
                    }

                    //log.d(this, URL.getPhotoURL(jsonObject.getString("photoPath")));

                    //TransForPicture picture=new TransForPicture();
                    //picture.execute(URL.getPhotoURL(jsonObject.getString("photoPath")));

                    TransForGetSchoolProject transForGetSchoolProject = new TransForGetSchoolProject();
                    transForGetSchoolProject.execute();

                    EventBus.getDefault().post(new LoginResultEvent(true));
                } else {
                    EventBus.getDefault().post(new LoginResultEvent(false));
                }
            } catch (Exception e) {
                EventBus.getDefault().post(new LoginResultEvent(false));
            }
        } else {
            EventBus.getDefault().post(new LoginResultEvent(false));
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
