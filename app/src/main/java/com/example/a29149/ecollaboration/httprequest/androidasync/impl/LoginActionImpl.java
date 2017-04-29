package com.example.a29149.ecollaboration.httprequest.androidasync.impl;

import com.example.a29149.ecollaboration.httprequest.myinterface.LoginAction;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.URL;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

import org.json.JSONObject;


/**
 * Created by 29149 on 2017/3/8.
 */

public class LoginActionImpl implements LoginAction {
    @Override
    public void login(String userName, String passWord)
    {
        AsyncHttpClient.getDefaultInstance().executeJSONObject(
                new AsyncHttpGet(URL.getLoginURL(userName, passWord)),
                new AsyncHttpClient.JSONObjectCallback() {
                    @Override
                    public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                        if (e!=null)
                        {
                            return;
                        }
                        log.d(LoginActionImpl.this, result.toString());

                    }
                }
        );
    }
}
