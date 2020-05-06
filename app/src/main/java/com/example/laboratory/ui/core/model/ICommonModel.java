package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;

/**
 * 通用数据接口
 */
public interface ICommonModel {




    /**
     * 根据学院获取用户列表
     * @param departId
     * @param permission
     * @param rxObserver
     */
    void getUsersByDepartId(String departId,String permission, RxObserver<UserList> rxObserver);


    /**
     * 根据学院获取用户列表
     * @param rxObserver
     */
    void getAllUsers(RxObserver<UserList> rxObserver);

    /**
     * 根据取用户列表
     * @param permission
     * @param rxObserver
     */
    void getUsersPermissionNot(String permission, RxObserver<UserList> rxObserver);


    /**
     * 获得实验室列表
     * @param page
     * @param size
     * @param uid
     * @param departId
     * @param rxObserver
     */
    void getLaboratoryList(int page,int size,String uid,String departId, RxObserver<Laboratory> rxObserver);


    void getLabsData(RxObserver<LaboratoryList> rxObserver, String id, String cate);
    void getDepartList(RxObserver<Depart> callback);



    void deleteXjRecordByXjid(String xjid,RxObserver<String> callback);
}
