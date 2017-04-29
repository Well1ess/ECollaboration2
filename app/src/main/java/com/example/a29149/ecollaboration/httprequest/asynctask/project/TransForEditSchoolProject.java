package com.example.a29149.ecollaboration.httprequest.asynctask.project;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.mainactivity.HttpResponseEvent;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.URL;
import com.example.a29149.ecollaboration.util.log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/26 0026.
 * Description: edit school project
 */

public class TransForEditSchoolProject extends AsyncTask<String, Integer, String> {
    Activity mActivity;

    public TransForEditSchoolProject(Activity activity) {
        this.mActivity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getModifyProjectInfoAction(params[0], params[1], params[2], params[3],
                    params[4], params[5], params[6], params[7],
                    params[8], params[9], params[10], params[11]));
            log.d(this, URL.getModifyProjectInfoAction(params[0], params[1], params[2], params[3],
                    params[4], params[5], params[6], params[7],
                    params[8], params[9], params[10], params[11]));
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

                    mActivity.finish();

                    EventBus.getDefault().post(new HttpResponseEvent(true, "修改工程成功！"));

                } else {
                    EventBus.getDefault().post(new HttpResponseEvent(false, "修改工程失败！"));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }
    }
}
