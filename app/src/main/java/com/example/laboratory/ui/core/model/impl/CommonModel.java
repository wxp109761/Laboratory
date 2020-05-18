package com.example.laboratory.ui.core.model.impl;


import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.ICommonModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

import java.util.HashMap;



public class CommonModel extends BaseModel implements ICommonModel {

    //根据学院获取用户列表
    @Override
    public void getUsersByDepartId(String departId, String permission, RxObserver<UserList> rxObserver) {
        doRxRequest()
                .getUsersByDepartId("Bearer "+ UserInfoManager.getUserInfo().getToken(),departId,permission)
                .compose(RxSchedulers.<UserList>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getAllUsers(RxObserver<UserList> rxObserver) {
        doRxRequest()
               .getAllUsers("Bearer "+ UserInfoManager.getUserInfo().getToken())
                .compose(RxSchedulers.<UserList>io_main())
                .subscribe(rxObserver);
    }
    @Override
    public void getLaboratoryList(int page, int size, String uid,String departId, RxObserver<Laboratory> rxObserver) {
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("labUid",uid);
        paramsMap.put("departId",departId);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .getLaboratoryList("Bearer "+ UserInfoManager.getUserInfo().getToken(),page,size,body)
                .compose(RxSchedulers.<Laboratory>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getUsersPermissionNot(String permission, RxObserver<UserList> rxObserver) {
        doRxRequest()
                .getUsersPermissionNot("Bearer "+ UserInfoManager.getUserInfo().getToken(),permission)
                .compose(RxSchedulers.<UserList>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getLabsData(RxObserver<LaboratoryList> rxObserver, String id, String cate) {
        if(cate.equals("uid")){
            doRxRequest()
                    .getLabsByUid("Bearer "+ UserInfoManager.getUserInfo().getToken(),id)
                    .compose(RxSchedulers.<LaboratoryList>io_main())
                    .subscribe(rxObserver);
        }else if(cate.equals("depart_id")){
            doRxRequest()
                    .getLabsByDepart("Bearer "+ UserInfoManager.getUserInfo().getToken(),id)
                    .compose(RxSchedulers.<LaboratoryList>io_main())
                    .subscribe(rxObserver);
        }else if(cate.equals("all")){
            doRxRequest()
                    .getAllLabs("Bearer "+UserInfoManager.getUserInfo().getToken())
                    .compose(RxSchedulers.<LaboratoryList>io_main())
                    .subscribe(rxObserver);
        }

    }

    @Override
    public void getDepartList(RxObserver<Depart> callback) {
        doRxRequest()
                .getDepartInfo("Bearer "+UserInfoManager.getUserInfo().getToken())
                .compose(RxSchedulers.<Depart>io_main())
                .subscribe(callback);
    }

    @Override
    public void deleteXjRecordByXjid(String id, RxObserver<String> callback) {
        doRxRequest().
                DeleteXjRecordById("Bearer "+ UserInfoManager.getUserInfo().getToken(),id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

}
