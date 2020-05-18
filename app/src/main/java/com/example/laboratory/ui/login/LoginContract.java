package com.example.laboratory.ui.login;


import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IView;

/**
 * 登录、注册协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface LoginContract {

    interface IUserPresenter {
        void login(String jobNumber,String password);
        void register();

    }


    interface ILoginRegisterView extends IView {

    }
}
