package com.example.laboratory.root_ui.UserInfoList;

import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.ui.core.view.IPageLoadDataView;

public interface UserInfoListContact {
    interface IUserInfoListPresenter{
//        void getUserList(String departId,String permission);
        void getUserListPermissionNot(String permission);
        void getUserExceptSelf(String uid);
        void updateUserInfo(String uid, User user);
    }
    interface IUserInfoListView extends IPageLoadDataView<UserList.UserListBean> {
    }
}
