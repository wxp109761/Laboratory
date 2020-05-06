package com.example.laboratory.ui.core.model;


import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.User;
import com.example.laboratory.inter.VerifyAccountCallback;
import com.example.laboratory.net.callback.RxObserver;

/**
 *
 */

public interface ILoginModel {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password, RxObserver<User> callback);


      /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     */
    void register(String username, String password,RxObserver<String> callback);



    /**
     * 保存用户信息
     * @param user 用户
     */
    void saveUserInfo(User user);

    /**
     * 账号密码判空
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean verifyAccount(String username, String password, VerifyAccountCallback callback);
}
