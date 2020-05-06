package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.Messages;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IMessageModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

public class MessageModel extends BaseModel implements IMessageModel {


    @Override
    public void getAllMessages(RxObserver<Messages> rxObserver, String uid) {
        doRxRequest()
                .getAllMessages("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid)
                .compose(RxSchedulers.<Messages>io_main())
                .subscribe(rxObserver);
    }
    @Override
    public void getUnReadMessages(RxObserver<Messages> rxObserver, String uid) {
        doRxRequest()
                .getUnReadMessages("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid)
                .compose(RxSchedulers.<Messages>io_main())
                .subscribe(rxObserver);
    }
    @Override
    public void sendMessage(Messages.MessageListBean messageListBean, String send_id, String receive_id, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(messageListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .sendMessage("Bearer "+ UserInfoManager.getUserInfo().getToken(),body,send_id,receive_id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }



    @Override
    public void setMessageRead(String id, String userId, RxObserver<String> rxObserver) {
        doRxRequest()
                .setMessageRead("Bearer "+ UserInfoManager.getUserInfo().getToken(),id,userId)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }
}
