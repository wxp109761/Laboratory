package com.example.laboratory.root_ui.AddUser;

import com.example.laboratory.bean.UserList;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

public class AddUserContact {
    interface IAddUserPresenter{
        void addUser(UserList.UserListBean user);
    }
    interface IAddUserView extends IView {
        void showResult(String msg);
    }
}
