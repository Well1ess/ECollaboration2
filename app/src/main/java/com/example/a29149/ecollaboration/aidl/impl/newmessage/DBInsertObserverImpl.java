package com.example.a29149.ecollaboration.aidl.impl.newmessage;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.example.a29149.ecollaboration.aidl.BinderPoolCode;
import com.example.a29149.ecollaboration.aidl.IMsgObserver;
import com.example.a29149.ecollaboration.dto.MyMessage;

/**
 * Created by 29149 on 2017/3/17.
 */

public class DBInsertObserverImpl extends IMsgObserver.Stub {

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case BinderPoolCode.NEW_MESSAGE:
                    Log.d("DBObserver",""+msg.obj.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    public void sendMessage(MyMessage msg) throws RemoteException {
        handler.obtainMessage(BinderPoolCode.NEW_MESSAGE, msg).sendToTarget();
    }

}
