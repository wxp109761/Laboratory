package com.example.laboratory.ui.core.model.impl;

import android.util.Log;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.INoticeModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

public class NoticeModel extends BaseModel implements INoticeModel {



    @Override
    public void addNotice(Notices.NoticesListBean noticesListBean, String send_Id, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(noticesListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .AddNotice("Bearer "+ UserInfoManager.getUserInfo().getToken(),body,send_Id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }
    @Override
    public void getNoticesData(RxObserver<Notices> rxObserver, String uid, String send_depart, String category,String send_id) {
        if(category.equals("getAll")){
            Log.d("zzz",send_depart);
            Integer send_depart_id=Integer.parseInt(send_depart);
            doRxRequest()
                    .getAllNotices("Bearer "+ UserInfoManager.getUserInfo().getToken(),send_depart_id)
                    .compose(RxSchedulers.<Notices>io_main())
                    .subscribe(rxObserver);
        }else if(category.equals("adminGetAll")){
            doRxRequest()
                    .adminGetAllNotices("Bearer "+ UserInfoManager.getUserInfo().getToken(),send_id,send_depart)
                    .compose(RxSchedulers.<Notices>io_main())
                    .subscribe(rxObserver);
        } else if(category.equals("getUnRead")){
            doRxRequest()
                    .getUnReadNotices("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid)
                    .compose(RxSchedulers.<Notices>io_main())
                    .subscribe(rxObserver);
        }else if(category.equals("getByDepart")){
            doRxRequest()
                    .getBySendDepart("Bearer "+ UserInfoManager.getUserInfo().getToken(),send_depart)
                    .compose(RxSchedulers.<Notices>io_main())
                    .subscribe(rxObserver);
        }if(category.equals("getBySendId")){
            doRxRequest()
                    .getBySendId("Bearer "+ UserInfoManager.getUserInfo().getToken(),send_id)
                    .compose(RxSchedulers.<Notices>io_main())
                    .subscribe(rxObserver);
        }

    }


    @Override
    public void setRead(Integer id, String userId, RxObserver<String> rxObserver) {
        doRxRequest()
                .setRead("Bearer "+ UserInfoManager.getUserInfo().getToken(),id,userId)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }
}
