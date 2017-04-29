package com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.teamResponse;

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
 * Created by 张丽华 on 2016/12/26 0026.
 * Description: get apply who apply my team
 */

public class TransForGetRefuseApplyTeam extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getAppRefuseJoinTeamApply(params[0]));
            log.d(this, URL.getAppRefuseJoinTeamApply(params[0]));
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
        if(result!=null)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(result);
                String resultFlag = jsonObject.getString("result");
                if (resultFlag.equals("success")) {


                    EventBus.getDefault().post(new HttpResponseEvent(true, "已回绝该团队申请"));
                    //TO:HistoryApplyFragment
                } else {
                    EventBus.getDefault().post(new HttpResponseEvent(false, "回复失败！"));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }
    }
}
