package com.example.a29149.ecollaboration.aidl.impl;

import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.a29149.ecollaboration.aidl.BinderPoolCode;
import com.example.a29149.ecollaboration.aidl.IBinderPool;
import com.example.a29149.ecollaboration.aidl.impl.newmessage.NewMessageImpl;
import com.example.a29149.ecollaboration.aidl.impl.sendmessage.SendMessageImpl;


/**
 * Created by 29149 on 2017/3/17.
 */

public class BinderPoolImpl extends IBinderPool.Stub {

    private Handler handler;

    public BinderPoolImpl(Handler handler)
    {
        this.handler=handler;
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        switch (binderCode)
        {
            case BinderPoolCode.NEW_MESSAGE:
                return new NewMessageImpl();
            case BinderPoolCode.SEND_MESSAGE:
                return new SendMessageImpl(handler);
        }
        return null;
    }
}
