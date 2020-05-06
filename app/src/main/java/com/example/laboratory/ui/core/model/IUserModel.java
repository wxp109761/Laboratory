package com.example.laboratory.ui.core.model;


import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.Record;
import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;

import java.util.List;



public interface IUserModel {


    /**
     * 获得record
     * @param uid
     * @param rxObserver
     */
    void getRecordList(String uid, RxObserver<Record> rxObserver);



    void addUser(UserList.UserListBean userListBean,RxObserver<String> callback);


    void getUserExceptSelf(RxObserver<UserList> rxObserver,String uid);


}
