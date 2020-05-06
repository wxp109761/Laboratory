package com.example.laboratory.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.utils.LightStatusbarUtils;
import com.example.laboratory.utils.ToastUtils;


/**
 * 登录、注册
 * Created by 康栋普 on 2018/2/1.
 */

public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginContract.ILoginRegisterView {
    private EditText et_job_number, et_password;


    @Override
    public String getJobNumber() {
        return et_job_number.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return et_password.getText().toString().trim();
    }

    @Override
    public void showResult(String msg) {
        ToastUtils.showToast(AppContext.getContext(), msg);
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
        finish();
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initViews() {
        et_job_number =  findViewById(R.id.login_input_username);
        et_password =  findViewById(R.id.login_input_password);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();

            }
        });

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LightStatusbarUtils.setLightStatusBar(this, true);
    }



}
