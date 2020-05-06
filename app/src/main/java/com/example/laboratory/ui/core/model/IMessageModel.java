package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Messages;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.net.callback.RxObserver;

public interface IMessageModel {
    void getAllMessages(RxObserver<Messages> rxObserver,String uid);
    void sendMessage(Messages.MessageListBean messageListBean, String send_Id,String receive_id, RxObserver<String> callback);
    void getUnReadMessages(RxObserver<Messages> rxObserver, String uid);
    void setMessageRead(String id, String userId, RxObserver<String> rxObserver);
}
