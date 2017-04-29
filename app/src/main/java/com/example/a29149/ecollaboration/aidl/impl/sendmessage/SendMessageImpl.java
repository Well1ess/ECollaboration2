package com.example.a29149.ecollaboration.aidl.impl.sendmessage;

import android.os.Handler;
import android.os.RemoteException;

import com.example.a29149.ecollaboration.aidl.BinderPoolCode;
import com.example.a29149.ecollaboration.aidl.ISendMessage;

/**
 * Created by 29149 on 2017/3/19.
 */

public class SendMessageImpl extends ISendMessage.Stub {
    private Handler handler;

    public SendMessageImpl(Handler handler)
    {
        this.handler=handler;
    }

    @Override
    public void sendMessage(String msg) throws RemoteException {
        handler.obtainMessage(BinderPoolCode.SERVICE_SEND_MESSAGE_HANDLER,msg).sendToTarget();
    }
}
