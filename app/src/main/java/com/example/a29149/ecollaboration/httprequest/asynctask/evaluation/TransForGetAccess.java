package com.example.a29149.ecollaboration.httprequest.asynctask.evaluation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.dto.ProjectAccessTypeDTO;
import com.example.a29149.ecollaboration.dto.StudentDTO;
import com.example.a29149.ecollaboration.dto.StudentScoreDTO;
import com.example.a29149.ecollaboration.mainactivity.HttpResponseEvent;
import com.example.a29149.ecollaboration.model.evaluation.EvaluationActivity;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.URL;
import com.example.a29149.ecollaboration.util.log;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 张丽华 on 2017/4/19.
 * Description: 用于上传评估的分数
 */

public class TransForGetAccess extends AsyncTask<String, Integer, String> {
    private Activity mActivity;

    public TransForGetAccess(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getAccess(params[0], params[1]));
            log.d(this, URL.getAccess(params[0], params[1]));
            con = (HttpURLConnection) url.openConnection();

            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setRequestMethod("GET");
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

                    java.lang.reflect.Type access = new com.google.gson.reflect.TypeToken<List<ProjectAccessTypeDTO>>() {
                    }.getType();
                    List<ProjectAccessTypeDTO> assess = new Gson().fromJson(jsonObject.getString("accessTypeDTOList"), access);

                    java.lang.reflect.Type student = new com.google.gson.reflect.TypeToken<List<StudentDTO>>() {
                    }.getType();
                    List<StudentDTO> studentBeans = new Gson().fromJson(jsonObject.getString("studentDTOList"), student);

                    int studentNum = Integer.parseInt(jsonObject.getString("studentNum"));


                    List<List<StudentScoreDTO>> studentsScoreDTO = new ArrayList<>();

                    java.lang.reflect.Type score = new com.google.gson.reflect.TypeToken<List<StudentScoreDTO>>() {
                    }.getType();

                    for (int i = 0; i < studentNum; i++) {
                        String tag = "studentScore" + i;
                        List<StudentScoreDTO> studentScoreDTOs = new Gson().fromJson(jsonObject.getString(tag), score);
                        studentsScoreDTO.add(studentScoreDTOs);
                    }

                    GlobalUtil.getInstance().setProjectAccessTypeDTOs(assess);
                    GlobalUtil.getInstance().setStudentsBeans(studentBeans);
                    GlobalUtil.getInstance().setStudentsScoreDTO(studentsScoreDTO);

                    Intent intent = new Intent(mActivity, EvaluationActivity.class);
                    mActivity.startActivity(intent);

                } else {
                    EventBus.getDefault().post(new HttpResponseEvent(false, "评估失败！"));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }
    }
}
