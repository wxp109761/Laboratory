package com.example.laboratory.ui.messages;

import com.example.laboratory.bean.Messages;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.MessageModel;
import com.example.laboratory.ui.core.model.impl.NoticeModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class MessagePresenter extends  BasePresenter<MessageContract.IMessageView> implements MessageContract.IMessagePresenter  {
    private MessageModel messageModel;
    private MessageContract.IMessageView messageView;
    public MessagePresenter(){
        this.messageModel=new MessageModel();
    }



    RxObserver<Messages> RxGetData(String cate){
        messageView =getView();
        RxObserver<Messages> rxObserver=new RxObserver<Messages>(this) {
            @Override
            protected void onSuccess(Messages data) {
                List<Messages.MessageListBean> messageList=data.getMessageList();
                if(cate.equals("all")){
                    messageView.setData(messageList);
                    if (messageView.getData().size() == 0){
                        messageView.showEmpty();
                    } else
                        messageView.showContent();
                }else if(cate.equals("unRead")){
                    messageView.setUnMessList(messageList);
                }

            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                messageView.showFail(errorMsg);
            }
        };
        return rxObserver;
    }


    @Override
    public void getAllMessage(String uid) {
        RxObserver<Messages> rxObserver=RxGetData("all");
        messageModel.getUnReadMessages(rxObserver,uid);
        addDisposable(rxObserver);
    }

    @Override
    public void getUnReadMessages(String uid) {
        RxObserver<Messages> rxObserver=RxGetData("unRead");
        messageModel.getAllMessages(rxObserver,uid);
        addDisposable(rxObserver);
    }

    @Override
    public void sendMessage(Messages.MessageListBean messageListBean, String send_id, String receive_id) {
        messageView =getView();
        RxObserver<String> sendObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                messageView.showResult("发送成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                messageView.showFail(errorMsg);
            }
        };
        messageModel.sendMessage(messageListBean,send_id,receive_id,sendObserver);
        addDisposable(sendObserver);
    }

    @Override
    public void setMessageRead(String id, String uid) {
        messageView =this.getView();
        RxObserver<String> readObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                messageView.showResult("已读");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                messageView.showFail(errorMsg);
            }
        };
        messageModel.setMessageRead(id,uid,readObserver);
        addDisposable(readObserver);
    }
}
