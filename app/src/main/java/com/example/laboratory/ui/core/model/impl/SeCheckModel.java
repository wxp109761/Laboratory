package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.*;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.ISeCheckModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

import java.util.List;

public class SeCheckModel extends CommonModel implements ISeCheckModel {

    @Override
    public void getItemsByLabId(String labId, RxObserver<Items> rxObserver) {
        doRxRequest()
                .getItemsByLabId("Bearer "+ UserInfoManager.getUserInfo().getToken(),labId)
                .compose(RxSchedulers.<Items>io_main())
                .subscribe(rxObserver);

    }

    @Override
    public void addRecordsResult(List<Xjresult> results, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(results);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .addResultList(body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

    @Override
    public void addRecords(Xjrecord xjrecord, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(xjrecord);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .addRecord(body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

}
