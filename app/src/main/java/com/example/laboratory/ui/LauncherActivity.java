package com.example.laboratory.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import android.view.WindowManager;
import android.widget.EditText;
import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.login.LoginActivity;
import com.example.laboratory.ui.login.LoginContract;
import com.example.laboratory.ui.login.LoginPresenter;
import com.example.laboratory.ui.main.MainActivity;

import java.lang.ref.WeakReference;


public class LauncherActivity extends BaseActivity {


    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle bundle) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
//        startService(new Intent(this, AlarmService.class));
//        OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this)
//                .setDrawStategy(new NormalDrawStrategy()) //设置动画效果
//                .setAppIcon(res.getDrawable(R.drawable.ic_launcher)) //设置图
////                .setColorOfAppIcon() //设置绘制图标线条的颜色
////                .setAppName("Do it") //设置app名称
//                .setColorOfAppName(R.color.icon_color) //设置app名称颜色
//                .setAppStatement("生命不息，奋斗不止") //设置一句话描述
//                .setColorOfAppStatement(R.color.icon_color) // 设置一句话描述的颜色
//                .create();
//        openingStartAnimation.show(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (!UserInfoManager.isLogin()){
                    Intent mainIntent = new Intent(LauncherActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
