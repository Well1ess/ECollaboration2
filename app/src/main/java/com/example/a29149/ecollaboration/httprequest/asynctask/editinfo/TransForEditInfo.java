package com.example.a29149.ecollaboration.httprequest.asynctask.editinfo;

import android.os.AsyncTask;

import com.example.a29149.ecollaboration.dto.StudentDTO;
import com.example.a29149.ecollaboration.dto.TeacherDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForGetMyJoinTeams;
import com.example.a29149.ecollaboration.mainactivity.EditInfoEvent;
import com.example.a29149.ecollaboration.mainactivity.HttpResponseEvent;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.URL;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import de.greenrobot.event.EventBus;

/**
 * Created by 张丽华 on 2016/12/22 0022.
 * Description: edit self info
 */

public class TransForEditInfo extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {

            /**
             * 请求地址
             */

            java.net.URL url = new java.net.URL(URL.getEditUserInfoActionURL(params[0], params[1], params[2], params[3], params[4], params[5]));
            con = (HttpURLConnection) url.openConnection();
            log.d(this, URL.getEditUserInfoActionURL(params[0], params[1], params[2], params[3], params[4], params[5]));
            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "multipart/form-data");
            con.setRequestProperty("cookie", GlobalUtil.getInstance().getSessionId());


            // 获得服务端的返回数据
            InputStreamReader read = new InputStreamReader(con.getInputStream());
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
                if (resultFlag.equals("success")) {
                    if (GlobalUtil.getInstance().getRole().equals("teacher")) {

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                        }.getType();
                        TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("teacherBean"), type);
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                        GlobalUtil.getInstance().setUserDTO(teacherDTO);
                    } else {
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                        }.getType();
                        StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentBean"), type);
                        GlobalUtil.getInstance().setStudentDTO(studentDTO);
                        GlobalUtil.getInstance().setUserDTO(studentDTO);

                        TransForGetMyJoinTeams joinTeams = new TransForGetMyJoinTeams();
                        joinTeams.execute();
                    }
                    //TO:MainActivity,UserInfoEditActivity
                    EventBus.getDefault().post(new EditInfoEvent(true));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }

    }
}
