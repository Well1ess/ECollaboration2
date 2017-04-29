package com.example.a29149.ecollaboration.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.a29149.ecollaboration.MainService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 29149 on 2017/3/17.
 */

public class BinderPoolUtil {

    private Context context;
    private IBinderPool iBinderPool;//用于接受服务器端返回的Binder
    private volatile static BinderPoolUtil binderPoolUtil;
    private CountDownLatch countDownLatch;//同步辅助类

    public BinderPoolUtil(Context context)
    {
        this.context=context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPoolUtil getInstance(Context context)
    {
        if (binderPoolUtil==null)
        {
            synchronized (BinderPoolUtil.class)
            {
                if (binderPoolUtil==null)
                {
                    binderPoolUtil=new BinderPoolUtil(context);
                }
            }
        }
        return binderPoolUtil;
    }

    private synchronized void connectBinderPoolService()
    {
        countDownLatch=new CountDownLatch(1);
        Intent services =new Intent(context,MainService.class);

        context.startService(services);
        context.bindService(services, serviceConnection, Context.BIND_AUTO_CREATE);

        try {
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public synchronized void disconnectService()
    {
        context.unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iBinderPool=IBinderPool.Stub.asInterface(service);
            try
            {
                iBinderPool.asBinder().linkToDeath(deathRecipient, 0);
            }catch (RemoteException e)
            {
                e.printStackTrace();
            }
            countDownLatch.countDown();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            iBinderPool.asBinder().unlinkToDeath(deathRecipient, 0);
            iBinderPool=null;
            connectBinderPoolService();
        }
    };

    public IBinder getServiceBinder(int binderCode)
    {
        IBinder iBinder=null;

        try
        {
            if (iBinderPool!=null)
            {
                iBinder=iBinderPool.queryBinder(binderCode);
            }
        }catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return iBinder;
    }
}
