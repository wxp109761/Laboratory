package com.example.laboratory.ui.core.model.impl;

import android.text.TextUtils;

import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.User;
import com.example.laboratory.inter.VerifyAccountCallback;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.ILoginModel;
import com.google.gson.Gson;
import okhttp3.RequestBody;

import java.util.HashMap;


/**
 * Created by 康栋普 on 2018/2/1.
 */

public class LoginModel extends BaseModel implements ILoginModel {

    /**
     * 登录
     * @param jobNumber 用户名
     * @param password 密码
     * @param callback
     */
    @Override
    public void login(String jobNumber, String password, RxObserver<User> callback) {
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("jobNumber",jobNumber);
        paramsMap.put("password",password);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        doRxRequest()
                .login(body)
                .compose(RxSchedulers.<User>io_main())
                .subscribe(callback);
    }

    /**
     * 注册
     * @param username   用户名
     * @param password   密码
     * @param callback
     */
    @Override
    public void register(final String username, final String password,  RxObserver<String> callback) {
//        doRxRequest()
//                .register(username, password, password)
//                .compose(RxSchedulers.<String>io_main())
//                .subscribe(callback);
    }



    /**
     * 保存用户信息
     * @param user
     */
    @Override
    public void saveUserInfo(User user) {
        //加密保存用户信息和密钥
        UserInfoManager.saveUserInfo(user);
        UserInfoManager.saveIsLogin(true);
    }



    /**
     * 账号密码判空
     * @param username
     * @param password
     * @param callback
     * @return
     */
    @Override
    public boolean verifyAccount(String username, String password, VerifyAccountCallback callback) {
        if (TextUtils.isEmpty(username)) {
            callback.onVerifyResult(AppContext.getContext().getString(R.string.username_not_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            callback.onVerifyResult(AppContext.getContext().getString(R.string.password_not_empty));
            return false;
        }
        return true;
    }

}
