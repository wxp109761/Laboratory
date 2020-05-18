package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Remind;
import com.example.laboratory.net.callback.RxObserver;

public interface IRemindModel {
    void addRemind(RxObserver<String> rxObserver, Remind.RemindListBean remindListBean);
    void deleteRemindid(RxObserver<String> rxObserver, Integer id);
    void updateRemindid(RxObserver<String> rxObserver, Integer id,Remind.RemindListBean remindListBean);
    void getRemindidByUid(RxObserver<Remind> rxObserver, String uid);
}
