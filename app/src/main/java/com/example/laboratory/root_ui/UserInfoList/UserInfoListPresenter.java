package com.example.laboratory.root_ui.UserInfoList;

import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.UserModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class UserInfoListPresenter extends BasePresenter<UserInfoListContact.IUserInfoListView> implements UserInfoListContact.IUserInfoListPresenter {

    private UserModel userModel;
    private UserInfoListContact.IUserInfoListView iUserInfoListView;
    public UserInfoListPresenter(){
        this.userModel=new UserModel();
    }

    @Override
    public void getUserExceptSelf(String uid) {
        RxObserver<UserList> userListRxObserver=getUserRxo();
        userModel.getUserExceptSelf(userListRxObserver,uid);
        addDisposable(userListRxObserver);
    }

    RxObserver<UserList> getUserRxo(){
        iUserInfoListView= getView();
        RxObserver<UserList> usersRxObserver=new RxObserver<UserList>(this) {
            @Override
            protected void onSuccess(UserList data) {
                List<UserList.UserListBean> userListBeans=data.getUserList();

                iUserInfoListView.setData(userListBeans);
                if (iUserInfoListView.getData().size() == 0)
                    iUserInfoListView.showEmpty();
                else
                    iUserInfoListView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iUserInfoListView.showFail(errorMsg);
            }
        };
        return usersRxObserver;
    }


    @Override
    public void getUserListPermissionNot(String permission) {
        RxObserver<UserList> userListRxObserver=getUserRxo();
        userModel.getUsersPermissionNot(permission,userListRxObserver);
        addDisposable(userListRxObserver);
    }
}
