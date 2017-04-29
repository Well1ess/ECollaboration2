package com.example.a29149.ecollaboration.httprequest.asynctask.editinfo;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.URL;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by 张丽华 on 2016/12/22 0022.
 */

public class TransForEditPhoto extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {

            /**
             * 请求地址
             */

            java.net.URL url = new java.net.URL(URL.getEditPhotoAction(params[0], " "));
            con = (HttpURLConnection) url.openConnection();
            log.d(this, URL.getEditPhotoAction(params[0], " "));
            // 设置允许输出，默认为false
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "multipart/form-data");
            con.setRequestProperty("cookie", GlobalUtil.getInstance().getSessionId());

            DataOutputStream outStream = new DataOutputStream(con.getOutputStream());

            outStream.write(Base64.decode(params[1], Base64.DEFAULT));
            outStream.flush();
            outStream.close();

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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        log.d(this, s);
    }
}
