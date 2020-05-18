package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.Remind;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IRemindModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

public class ReminModel extends BaseModel implements IRemindModel {
    @Override
    public void addRemind(RxObserver<String> rxObserver, Remind.RemindListBean remindListBean) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(remindListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .addRemind("Bearer "+ UserInfoManager.getUserInfo().getToken(),body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void deleteRemindid(RxObserver<String> rxObserver, Integer id) {
        doRxRequest()
                .deleteRemindById("Bearer "+ UserInfoManager.getUserInfo().getToken(),id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void updateRemindid(RxObserver<String> rxObserver, Integer id,Remind.RemindListBean remindListBean) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(remindListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .updateRemind("Bearer "+ UserInfoManager.getUserInfo().getToken(),id,body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getRemindidByUid(RxObserver<Remind> rxObserver, String uid) {
        doRxRequest()
                .getRemindByUid("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid)
                .compose(RxSchedulers.<Remind>io_main())
                .subscribe(rxObserver);
    }
}
