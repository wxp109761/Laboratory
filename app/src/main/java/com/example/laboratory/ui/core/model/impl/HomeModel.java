package com.example.laboratory.ui.core.model.impl;
import com.example.laboratory.bean.*;

import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IHomeModel;


public class HomeModel extends CommonModel implements IHomeModel {
    @Override
    public void getLaboratoryList(int page, int size, String uid, String departId, RxObserver<Laboratory> rxObserver) {
        super.getLaboratoryList(page, size, uid, departId, rxObserver);
    }

    @Override
    public void getLabsData(RxObserver<LaboratoryList> rxObserver, String id, String cate) {
        super.getLabsData(rxObserver, id, cate);
    }


    @Override
    public void getUsersByDepartId(String departId, String permission, RxObserver<UserList> rxObserver) {
        super.getUsersByDepartId(departId, permission, rxObserver);
    }

    @Override
    public void getAllUsers(RxObserver<UserList> rxObserver) {
        super.getAllUsers(rxObserver);
    }

    @Override
    public void getUsersPermissionNot(String permission, RxObserver<UserList> rxObserver) {
        super.getUsersPermissionNot(permission, rxObserver);
    }

    @Override
    public void getDepartList(RxObserver<Depart> callback) {
        super.getDepartList(callback);
    }


}