package com.example.laboratory.ui.login;

import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.User;

import com.example.laboratory.inter.VerifyAccountCallback;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.LoginModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;



public class LoginPresenter extends BasePresenter<LoginContract.ILoginRegisterView> implements LoginContract.IUserPresenter {
    private LoginModel loginModel;
    private LoginContract.ILoginRegisterView mLoginView;

    public LoginPresenter() {
        this.loginModel = new LoginModel();
    }

    /**
     * 登录
     */
    @Override
    public void login(String jobNumber,String password) {
//        if (!verifyAccount()) return;
        IView iView=getView();
        RxObserver<User> mLoginRxObserver = new RxObserver<User>(this) {
            @Override
            protected void onSuccess(User userBean) {
                userBean.setPassword(password);
                loginModel.saveUserInfo(userBean);
                iView.showResult("登录成功!");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iView.showFail(errorMsg);
            }
        };
        loginModel.login(jobNumber,password, mLoginRxObserver);
        addDisposable(mLoginRxObserver);

    }

    /**
     * 注册
     */
    @Override
    public void register() {
//        if (!verifyAccount()) return;
        RxObserver<String> mRegisterRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
                mLoginView.showLoading(AppContext.getContext().getString(R.string.isRegistering));
            }

            @Override
            protected void onSuccess(String data) {
                mLoginView.showResult(AppContext.getContext().getString(R.string.register_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mLoginView.showFail(errorMsg);
            }
        };
       // loginModel.register(jobNumber, password, mRegisterRxObserver);
        addDisposable(mRegisterRxObserver);
    }





    private VerifyAccountCallback mVerifyAccountCallback = new VerifyAccountCallback() {
        @Override
        public void onVerifyResult(String msg) {
            mLoginView.showFail(msg);
        }
    };



}
