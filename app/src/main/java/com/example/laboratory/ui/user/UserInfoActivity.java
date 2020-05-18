package com.example.laboratory.ui.user;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.utils.DateUtils;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.user_job_numeber)
    TextView userJobNumeber;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_tel)
    TextView userTel;
    @BindView(R.id.user_depart)
    TextView userDepart;
    @BindView(R.id.user_gmt_create)
    TextView userGmtCreate;
    @BindView(R.id.user_gmt_update)
    TextView userGmtUpdate;
    CommonUtils commonUtils=new CommonUtils();
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("用户信息");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void initViews() {
        Bundle bundle = this.getIntent().getExtras();
        ButterKnife.bind(this);
        UserList.UserListBean user = (UserList.UserListBean) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
        if (user!=null) {
            userJobNumeber.setText(user.getJobNumber());
            userName.setText(user.getUsername());
            userTel.setText(user.getTel());
            userDepart.setText(commonUtils.getDepartName(user.getDepartId()));
            userGmtCreate.setText(DateUtils.parseTime(user.getGmtCreate()));
            userGmtUpdate.setText(DateUtils.parseTime(user.getGmtUpdate()));
        }

    }


}
