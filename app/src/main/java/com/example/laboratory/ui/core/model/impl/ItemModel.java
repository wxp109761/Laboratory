package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.Items;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IItemModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;


public class ItemModel extends CommonModel implements IItemModel {


    @Override
    public void getAllItems(RxObserver<Items> rxObserver) {
        doRxRequest()
                .getAllItems("Bearer "+ UserInfoManager.getUserInfo().getToken())
                .compose(RxSchedulers.<Items>io_main())
                .subscribe(rxObserver);
    }
    @Override
    public void addItems(Items.ItemListBean itemListBean, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(itemListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .AddItems("Bearer "+ UserInfoManager.getUserInfo().getToken(),body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }
}
