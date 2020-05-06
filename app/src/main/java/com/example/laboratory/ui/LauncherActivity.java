package com.example.laboratory.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.login.LoginActivity;
import com.example.laboratory.ui.login.LoginContract;
import com.example.laboratory.ui.login.LoginPresenter;
import com.example.laboratory.ui.main.MainActivity;

import java.lang.ref.WeakReference;


/**
 * 启动页、程序入口
 * Created by 康栋普 on 2018/1/31.
 */

public class LauncherActivity extends BasePresenterActivity<LoginPresenter> implements LoginContract.ILoginRegisterView {
    private User user;
    private Handler mHandler;
    private DelayRunnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       hideBottomUIMenu();
        //倒计时
        startCountdown();
    }

    private void startCountdown() {
        mHandler = new Handler();
        mRunnable = new DelayRunnable(this);
        mHandler.postDelayed(mRunnable, 2000);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //自动登录
    private void autoLogin() {

        if (UserInfoManager.isLogin()) {
            user = UserInfoManager.getUserInfo();
            if (user != null)
                mPresenter.login();
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }


    private static class DelayRunnable implements Runnable {
        private WeakReference<LauncherActivity> mWeakReference;

        DelayRunnable(LauncherActivity instance) {
            mWeakReference = new WeakReference<>(instance);
        }

        @Override
        public void run() {
            LauncherActivity instance = mWeakReference.get();
            if (instance == null) return;
            instance.autoLogin();
        }
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }



    @Override
    public String getJobNumber() {
        return user.getJobNumber();
    }

    @Override
    public String getPassWord() {
        return user.getPassword();
    }


    @Override
    public void showResult(String msg) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}
