package com.example.laboratory.ui.home;

import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.HomeModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter  {

    private HomeModel homeModel;
    private HomeContract.IHomeView iHomeView;

    public HomePresenter() {
        this.homeModel = new HomeModel();
    }
    RxObserver<UserList> getUserRx(){
        iHomeView= getView();
        RxObserver<UserList> usersRxObserver=new RxObserver<UserList>(this) {
            @Override
            protected void onSuccess(UserList data) {
                List<UserList.UserListBean> userListBeans=data.getUserList();
                iHomeView.getUserList(userListBeans);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iHomeView.showFail(errorMsg);
            }
        };
        return usersRxObserver;
    }

    @Override
    public void getLabsData(String id, String cate) {
        iHomeView=getView();
        RxObserver<LaboratoryList> rxObserver = new RxObserver<LaboratoryList>(this) {
            @Override
            protected void onSuccess(LaboratoryList data) {
                List<LaboratoryList.LabListBean> labListBeans=data.getLabList();
                iHomeView.getLabList(labListBeans);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iHomeView.showFail(errorMsg);
            }

        };
        homeModel.getLabsData(rxObserver,id,cate);
        addDisposable(rxObserver);
    }


    @Override
    public void getAllUsers() {
        RxObserver<UserList> usersRxObserver=getUserRx();
        homeModel.getAllUsers(usersRxObserver);
        addDisposable(usersRxObserver);
    }



    @Override
    public void getUserListPermissionNot(String permission) {
        RxObserver<UserList> usersRxObserver=getUserRx();
        homeModel.getUsersPermissionNot(permission,usersRxObserver);
        addDisposable(usersRxObserver);
    }


    @Override
    public void getDepartList() {
        iHomeView=getView();
        RxObserver<Depart> mDepartInfoRxObserver = new RxObserver<Depart>(this) {
            @Override
            protected void onSuccess(Depart data) {
                List<Depart.DepartListBean> departListBeans=data.getDepartList();
                iHomeView.getDepartList(departListBeans);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iHomeView.showFail(errorMsg);
            }

        };
        homeModel.getDepartList(mDepartInfoRxObserver);
        addDisposable(mDepartInfoRxObserver);
    }

}
