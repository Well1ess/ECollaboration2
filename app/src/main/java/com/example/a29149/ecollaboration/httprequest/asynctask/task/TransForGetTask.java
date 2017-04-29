package com.example.a29149.ecollaboration.httprequest.asynctask.task;

import android.os.AsyncTask;

import com.example.a29149.ecollaboration.dto.TaskDTO;
import com.example.a29149.ecollaboration.mainactivity.HttpResponseEvent;
import com.example.a29149.ecollaboration.model.project.task.GetTaskListEvent;
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
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 张丽华 on 2017/3/2.
 * Description: 获取所有我创建的任务
 */

public class TransForGetTask extends AsyncTask<String, Integer, String> {

    public TransForGetTask() {
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getMyTaskByTeacher());
            log.d(this, URL.getMyTaskByTeacher());
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

                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<List<TaskDTO>>>() {
                    }.getType();

                    List<List<TaskDTO>> taskBeans = new Gson()
                            .fromJson(jsonObject.getString("taskBeans"), type);

                    for (List<TaskDTO> taskDTOList: taskBeans)
                    {
                        for (TaskDTO taskDTO : taskDTOList)
                        {
                            GlobalUtil.getInstance().addMyTask(taskDTO);
                        }
                    }
                    //TO:TaskFragment
                    EventBus.getDefault().post(new GetTaskListEvent(true));
                } else {
                    EventBus.getDefault().post(new HttpResponseEvent(false, "获取任务列表失败！"));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }
    }
}
