package com.example.a29149.ecollaboration.aidl.impl.newmessage;

import android.os.RemoteException;

import com.example.a29149.ecollaboration.MainService;
import com.example.a29149.ecollaboration.aidl.IMsgObserver;
import com.example.a29149.ecollaboration.aidl.INewMessage;



/**
 * Created by 29149 on 2017/3/17.
 */

public class NewMessageImpl extends INewMessage.Stub {

    @Override
    public boolean registerListener(IMsgObserver listener) throws RemoteException {
        MainService.msgObserverList.register(listener);
        if (MainService.countDownLatch.getCount()>0)
        {
            MainService.countDownLatch.countDown();
        }
        return true;
    }

    @Override
    public boolean unregisterListener(IMsgObserver listener) throws RemoteException {
        MainService.msgObserverList.unregister(listener);
        return true;
    }
}
