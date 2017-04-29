package com.example.a29149.ecollaboration.httprequest.asynctask.team;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.mainactivity.HttpResponseEvent;
import com.example.a29149.ecollaboration.model.team.CreateTeamEvent;
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
 * Created by 张丽华 on 2016/12/21 0018.
 * Description: edit team
 */

public class TransForEditTeam extends AsyncTask<String, Integer, String> {

    private Activity mActivity;
    private int mPosition = -1;

    public TransForEditTeam(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        mPosition = Integer.parseInt(params[4]);

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getEditTeamAction(params[0], params[1], params[2], params[3]));
            con = (HttpURLConnection) url.openConnection();
            log.d(this, URL.getEditTeamAction(params[0], params[1], params[2], params[3]));

            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("contentType", "utf-8");
            con.setRequestProperty("cookie", GlobalUtil.getInstance().getSessionId());

            log.d(this, GlobalUtil.getInstance().getSessionId());

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
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<TeamDTO>() {
                    }.getType();
                    TeamDTO teamDTO = new Gson().fromJson(jsonObject.getString("teamBean"), type);

                    GlobalUtil.getInstance().addTeam(teamDTO);

                    mActivity.finish();

                    if (mPosition != -1)
                        GlobalUtil.getInstance().getTeamDTO().remove(mPosition);
                    else
                        throw new IllegalArgumentException("position == -1!");

                    GlobalUtil.getInstance().addTeam(teamDTO);

                    //TO:MainActivity
                    EventBus.getDefault().post(new HttpResponseEvent(true, "修改团队信息成功！"));
                    //TO:TeamMainFragment
                    EventBus.getDefault().post(new CreateTeamEvent(true));
                } else {
                    EventBus.getDefault().post(new HttpResponseEvent(false, "修改团队信息失败！"));
                }
            } catch (JSONException e) {
                EventBus.getDefault().post(new HttpResponseEvent(false, "JSON解析异常！"));
            }
        } else {
            EventBus.getDefault().post(new HttpResponseEvent(false, "服务器返回结果异常！"));
        }

    }
}
