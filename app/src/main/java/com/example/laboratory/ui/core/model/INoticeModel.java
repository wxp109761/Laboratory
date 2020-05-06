package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Notices;
import com.example.laboratory.net.callback.RxObserver;

public interface INoticeModel {
    void addNotice(Notices.NoticesListBean noticesListBean, String send_Id,RxObserver<String> callback);
    void getNoticesData(RxObserver<Notices> rxObserver,String uid,String send_depart,String category,String send_id);
    void setRead(Integer id,String userId, RxObserver<String> rxObserver);

}
