package com.example.laboratory.ui.messages;

import com.example.laboratory.bean.Messages;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.ui.core.view.IListDataView;

import java.util.List;

public interface MessageContract {

    interface IMessagePresenter {
        void getAllMessage(String uid);
        void getUnReadMessages(String uid);
        void sendMessage(Messages.MessageListBean messageListBean, String send_id, String receive_id);
        void setMessageRead(String id, String uid);
    }
    interface IMessageView extends IListDataView<Messages.MessageListBean> {
        void setUnMessList(List<Messages.MessageListBean> messageList);
    }
}
