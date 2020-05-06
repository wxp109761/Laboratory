package com.example.laboratory.ui.home;

import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;

public class HomeContract {

    interface IHomePresenter {
        void getDepartList();
        void getAllUsers();
        void getLabsData(String id,String cate);

        void getUserListPermissionNot(String permission);
       // void getUsersByDepart(String uid,String departId);
    }
    interface IHomeView extends IView{

        void getDepartList(List<Depart.DepartListBean> departListeans);


        void getUserList(List<UserList.UserListBean> userListBeans);
        void getLabList(List<LaboratoryList.LabListBean> labListBeans);
    }
}
