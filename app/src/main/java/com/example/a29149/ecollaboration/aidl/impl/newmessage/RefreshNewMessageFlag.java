package com.example.a29149.ecollaboration.aidl.impl.newmessage;

import android.os.Handler;
import android.os.RemoteException;

import com.example.a29149.ecollaboration.aidl.IMsgObserver;
import com.example.a29149.ecollaboration.dto.MyMessage;

/**
 * Created by 29149 on 2017/3/19.
 */

public class RefreshNewMessageFlag extends IMsgObserver.Stub {

    public static final int NEW_MESSAGE_TEAM=0;
    private Handler handler;
    public RefreshNewMessageFlag(Handler handler)
    {
        this.handler=handler;
    }
    @Override
    public void sendMessage(MyMessage msg) throws RemoteException {
        handler.obtainMessage(NEW_MESSAGE_TEAM,null).sendToTarget();
    }
}
