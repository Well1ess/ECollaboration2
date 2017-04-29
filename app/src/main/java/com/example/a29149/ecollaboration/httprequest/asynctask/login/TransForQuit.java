package com.example.a29149.ecollaboration.httprequest.asynctask.login;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by Administrator on 2016/12/18 0018.
 */

public class TransForQuit extends AsyncTask<String, Integer, String> {

    private Activity mainActivity;

    public TransForQuit(Activity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            java.net.URL url = new java.net.URL(URL.getQuitActionURL());
            con = (HttpURLConnection) url.openConnection();
            log.d(this, URL.getQuitActionURL());
            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);

            con.setRequestMethod("GET");

            con.setRequestProperty("contentType", "GBK");
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
        mainActivity.finish();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
