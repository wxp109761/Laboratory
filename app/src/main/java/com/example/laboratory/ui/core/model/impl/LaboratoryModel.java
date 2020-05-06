package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.ILaboratoryModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

public class LaboratoryModel extends CommonModel implements ILaboratoryModel {
    @Override
    public void addLabItemRelaations(String labId, Integer itemId, RxObserver<String> callback) {
        doRxRequest()
                .AddLabItemRelaitions("Bearer "+ UserInfoManager.getUserInfo().getToken(),labId,itemId)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

    @Override
    public void addLabs(Laboratory.RowsBean rowsBean, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(rowsBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .addLaboratory(body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
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

    @Override
    public void getItemsByBelong(String belong, RxObserver<Items> rxObserver) {
        doRxRequest()
                . getItemsByBelong("Bearer "+ UserInfoManager.getUserInfo().getToken(),belong)
                .compose(RxSchedulers.<Items>io_main())
                .subscribe(rxObserver);

    }



    @Override
    public void getLabsData(RxObserver<LaboratoryList> rxObserver, String id, String cate) {
        super.getLabsData(rxObserver, id, cate);
    }


    /**
     * 删除实验室，及对应的巡检记录及对应的巡检结果集及对应的实验室待巡检条目关系
     * @param labid
     * @param callback
     */

    @Override
    public void deleteLabByid(String labid, RxObserver<String> callback) {
        doRxRequest()
                .deleteLabById("Bearer "+ UserInfoManager.getUserInfo().getToken(),labid)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }


    @Override
    public void deleteXjRecordByXjid(String id, RxObserver<String> callback) {
        super.deleteXjRecordByXjid(id, callback);
    }
}
