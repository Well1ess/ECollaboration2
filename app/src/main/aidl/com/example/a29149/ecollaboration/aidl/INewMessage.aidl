package com.example.a29149.ecollaboration.aidl;

import com.example.a29149.ecollaboration.aidl.IMsgObserver;

interface INewMessage {
    boolean registerListener(IMsgObserver listener);
    boolean unregisterListener(IMsgObserver listener);
}
