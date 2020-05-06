package com.example.laboratory.root_ui.AddUser;

import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.UserModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

public class AddUserPresenter extends BasePresenter<AddUserContact.IAddUserView> implements AddUserContact.IAddUserPresenter {
    private UserModel userModel;
    private AddUserContact.IAddUserView iAddUserView;

    public AddUserPresenter(){
        this.userModel=new UserModel();
    }


    @Override
    public void addUser(UserList.UserListBean user) {
        iAddUserView=this.getView();
        RxObserver<String> userObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                iAddUserView.showResult("增加成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iAddUserView.showFail(errorMsg);
            }
        };
        userModel.addUser(user,userObserver);
        addDisposable(userObserver);
    }




}
