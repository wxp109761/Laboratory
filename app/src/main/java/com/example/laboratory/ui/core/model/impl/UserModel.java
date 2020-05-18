package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.*;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IUserModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

import java.util.Date;


public class UserModel extends CommonModel implements IUserModel {

    @Override
    public void getRecordList(String uid, RxObserver<Record> rxObserver) {
        doRxRequest()
                .getRecordList("Bearer "+UserInfoManager.getUserInfo().getToken(),uid)
                .compose(RxSchedulers.<Record>io_main())
                .subscribe(rxObserver);
    }



    @Override
    public void getLaboratoryList(int page, int size, String uid, String departId, RxObserver<Laboratory> rxObserver) {
        super.getLaboratoryList(page, size, uid, departId, rxObserver);
    }



    @Override
    public void getUsersByDepartId(String departId, String permission, RxObserver<UserList> rxObserver) {
        super.getUsersByDepartId(departId, permission, rxObserver);
    }

    @Override
    public void getUsersPermissionNot(String permission, RxObserver<UserList> rxObserver) {
        super.getUsersPermissionNot(permission, rxObserver);
    }



    @Override
    public void addUser(UserList.UserListBean userListBean, RxObserver<String> callback) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(userListBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .AddUser("Bearer "+ UserInfoManager.getUserInfo().getToken(),body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

    @Override
    public void getUserExceptSelf(RxObserver<UserList> rxObserver, String uid) {
        doRxRequest()
                .getUserExceptSelf("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid)
                .compose(RxSchedulers.<UserList>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void updateUserInfo(RxObserver<String> rxObserver, String uid,User user) {
        Gson gson=new Gson();
        String strEntity = gson.toJson(user);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .updataUserInfo("Bearer "+ UserInfoManager.getUserInfo().getToken(),uid,body)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(rxObserver);
    }


    /**
     * 删除巡检记录及其下的对应结果
     */
    @Override
    public void deleteXjRecordByXjid(String id, RxObserver<String> callback) {
        super.deleteXjRecordByXjid(id, callback);
    }
}
