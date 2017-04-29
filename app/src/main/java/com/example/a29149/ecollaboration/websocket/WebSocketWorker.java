package com.example.a29149.ecollaboration.websocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.a29149.ecollaboration.aidl.BinderPoolCode;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class WebSocketWorker extends WebSocketClient {

    private Handler pushService;
    public WebSocketWorker(Handler pushService, URI serverUri, Draft draft) {
        super(serverUri, draft);
        this.pushService=pushService;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        Bundle bundle=new Bundle();
        bundle.putString("info",s);
        Message message=new Message();
        message.setData(bundle);
        message.what= BinderPoolCode.NEW_MESSAGE;
        pushService.sendMessage(message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
