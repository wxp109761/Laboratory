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
        void login();
        void register();

    }


    interface ILoginRegisterView extends IView {

        /**
         * 获取工号
         *
         * @return
         */
        String getJobNumber();

        /**
         * 获取密码
         *
         * @return
         */
        String getPassWord();

        /**
         * 登录或注册Result
         */
        void showResult(String msg);
    }
}
