package com.example.a29149.ecollaboration;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.a29149.ecollaboration.aidl.BinderPoolCode;
import com.example.a29149.ecollaboration.aidl.IMsgObserver;
import com.example.a29149.ecollaboration.aidl.impl.BinderPoolImpl;
import com.example.a29149.ecollaboration.dto.MyMessage;
import com.example.a29149.ecollaboration.websocket.WebSocketWorker;

import org.java_websocket.drafts.Draft_17;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 29149 on 2017/3/17.
 * to be used to provide main service
 */

public class MainService extends Service{

    public final static int listenerNum=2;//用于观察者个数

    public static CountDownLatch countDownLatch;//观察着全员注册完毕时才正式建立连接

    public static RemoteCallbackList<IMsgObserver> msgObserverList=new RemoteCallbackList<IMsgObserver>();

    private WebSocketWorker webSocketWorker;

    @Override
    public void onCreate() {
        super.onCreate();
        countDownLatch=new CountDownLatch(listenerNum);

        new Thread(new initSocket()).start();
    }

    private Handler centerHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case BinderPoolCode.SERVICE_NEW_MESSAGE_HANDLER:
                    receiveNewMessage(msg.getData().getString("info"));
                    break;
                case BinderPoolCode.SERVICE_SEND_MESSAGE_HANDLER:
                    sendNewMessage((String)msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    private void receiveNewMessage(String s)
    {
        final int N=msgObserverList.beginBroadcast();
        Log.d("AIDL", "msgObserverList:"+N);
        for (int i=0; i<N; i++)
        {
            IMsgObserver msgObserver=msgObserverList.getBroadcastItem(i);
            try
            {
                msgObserver.sendMessage(new MyMessage("张丽华", s+"", "z"));
            }catch (RemoteException e)
            {
                e.printStackTrace();
            }

        }
        msgObserverList.finishBroadcast();
    }

    private void sendNewMessage(String s)
    {
        webSocketWorker.send(s);
    }

    private void initWebSocket()
    {
        URI uri=null;
        try {
            uri = new URI("ws://10.53.233.222:2333");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketWorker=new WebSocketWorker(centerHandler, uri, new Draft_17());
        try {
            webSocketWorker.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BinderPoolImpl(centerHandler);
    }

    @Override
    public void onDestroy()
    {
        webSocketWorker.close();
        Log.d("Service", "onDestroy");
        super.onDestroy();
    }

    private class initSocket implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                countDownLatch.await();

            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            initWebSocket();
        }
    }

}
