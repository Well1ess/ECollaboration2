package com.example.a29149.ecollaboration.aidl;

import com.example.a29149.ecollaboration.dto.MyMessage;

interface IMsgObserver {
    void sendMessage(in MyMessage msg);
}
