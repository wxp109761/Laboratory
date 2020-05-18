package com.example.laboratory.ui.login;

import android.animation.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.core.view.IView;
import com.example.laboratory.ui.main.MainActivity;
import com.example.laboratory.utils.LightStatusbarUtils;
import com.example.laboratory.utils.NetWorkUtils;
import com.example.laboratory.utils.ToastUtils;
import com.example.laboratory.widget.Interpolator;
import es.dmoral.toasty.Toasty;


/**
 * 登录、注册
 * Created by 康栋普 on 2018/2/1.
 */

public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements IView {
    @BindView(R.id.input_login_name)
    EditText inputLoginName;
    @BindView(R.id.input_layout_name)
    LinearLayout inputLayoutName;
    @BindView(R.id.input_login_pwd)
    EditText inputLoginPwd;
    @BindView(R.id.input_layout_psw)
    LinearLayout inputLayoutPsw;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.login_remember)
    CheckBox loginRemember;
    @BindView(R.id.layout_check)
    LinearLayout layoutCheck;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private EditText et_job_number, et_password;
    private SharedPreferences.Editor editor;
    private SharedPreferences login_sp;

    private View mInputLayout;
    private View progress;






    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        mInputLayout = findViewById(R.id.login_input_layout);
        progress = findViewById(R.id.login_layout_progress);
        login_sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemenber = login_sp.getBoolean("remember_password",false);
        if(isRemenber){
            //将账号和密码都设置到文本中
            String account=login_sp.getString("account","");
            String password=login_sp.getString("password","");
            inputLoginName.setText(account);
            inputLoginPwd.setText(password);
            loginRemember .setChecked(true);

        }
        mBtnLogin.setOnClickListener(new animationOnClickListener(this, mBtnLogin));
    }


    @Override
    public void showResult(String msg) {
        final String username =inputLoginName.getText().toString();
        final String password = inputLoginPwd.getText().toString();

        if(msg.equals("登录成功!")){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            //记住密码
            editor=login_sp.edit();
            if(loginRemember.isChecked()){
                editor.putBoolean("remember_password",true);
                editor.putString("account",username);
                editor.putString("password",password);
            }else {
                editor.clear();
            }
            editor.apply();
            finish();
        }else {
            recovery();
            Toasty.error(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT, true).show();
        }



        ToastUtils.showToast(AppContext.getContext(), msg);
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
        finish();
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
    protected void onCreate(Bundle bundle) {
        setStatusBar();
        super.onCreate(bundle);
    }
    private void setStatusBar(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    class animationOnClickListener implements View.OnClickListener{
        private Context context;
        private TextView btnLogin;


        public animationOnClickListener(Context context, Button btnLogin){
            this.btnLogin = btnLogin;
            this.context = context;
        }
        private float mWidth, mHeight;
        private void inputAnimator(final View view, float w, float h) {

            AnimatorSet set = new AnimatorSet();

            ValueAnimator animator = ValueAnimator.ofFloat(0, w);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                            .getLayoutParams();
                    params.leftMargin = (int) value;
                    params.rightMargin = (int) value;
                    view.setLayoutParams(params);
                }
            });

            ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                    "scaleX", 1f, 0.5f);
            set.setDuration(1000);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.playTogether(animator, animator2);
            set.start();
            set.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    progress.setVisibility(View.VISIBLE);
                    progressAnimator(progress);
                    mInputLayout.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub

                }
            });

        }
        private void progressAnimator(final View view) {
            PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                    0.5f, 1f);
            PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                    0.5f, 1f);
            ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                    animator, animator2);
            animator3.setDuration(1000);
            animator3.setInterpolator(new Interpolator());
            animator3.start();

        }

        @Override
        public void onClick(View view){

            if(btnLogin.getVisibility() == View.VISIBLE){

                btnLogin.setVisibility(View.GONE);

                // 计算出控件的高与宽
                mWidth = btnLogin.getMeasuredWidth();
                mHeight = btnLogin.getMeasuredHeight();
                // 隐藏输入框
                inputLayoutName.setVisibility(View.INVISIBLE);
                inputLayoutPsw.setVisibility(View.INVISIBLE);

                inputAnimator(mInputLayout, mWidth, mHeight);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final String username =inputLoginName.getText().toString();
                        final String password = inputLoginPwd.getText().toString();

                        if (NetWorkUtils.isNetworkConnected(getApplication())) {

                            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//                                Toast.makeText(LoginActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                                Toasty.info(LoginActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT, true).show();
                                recovery();
                                return;
                            }
                            mPresenter.login(username,password);
                        } else {
                            recovery();
//                            Toast.makeText(LoginActivity.this, "无网络连接！", Toast.LENGTH_SHORT).show();
                            Toasty.error(LoginActivity.this, "无网络连接", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }, 2000);

            }

        }
    }
    /**
     * 恢复初始状态
     */
    private void recovery() {

        progress.setVisibility(View.GONE);
        mBtnLogin.setVisibility(View.VISIBLE);
        mInputLayout.setVisibility(View.VISIBLE);
        inputLayoutName.setVisibility(View.VISIBLE);
        inputLayoutPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }


}
