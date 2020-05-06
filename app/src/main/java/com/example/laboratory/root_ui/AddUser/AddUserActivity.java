package com.example.laboratory.root_ui.AddUser;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

public class AddUserActivity extends BasePresenterActivity<AddUserPresenter> implements AddUserContact.IAddUserView {
    @BindView(R.id.job_number)
    EditText jobNumber;
    @BindView(R.id.add_user_name)
    EditText addUserName;
    @BindView(R.id.init_password)
    EditText initPassword;
    @BindView(R.id.user_depart)
    TextView userDepart;
    @BindView(R.id.tel_phone)
    EditText telPhone;
    @BindView(R.id.permission)
    Spinner permission_spinner;
    @BindView(R.id.add_labs)
    Button addLabs;

    CommonUtils commonUtils = new CommonUtils();
    UserList.UserListBean user = new UserList.UserListBean();
    @BindView(R.id.depart_spinner)
    Spinner departSpinner;
    @BindView(R.id.tv_user_permission)
    TextView tvUserPermission;

    @Override
    protected AddUserPresenter createPresenter() {
        return new AddUserPresenter();
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("新增安全员");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user;
    }

    String permission_check = "";
    String depart_check = "";

    @Override
    protected void initViews() {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
            userDepart.setText(commonUtils.getDepartName(UserInfoManager.getUserInfo().getDepartId()));
            depart_check=userDepart.getText().toString();
        } else {
            userDepart.setVisibility(View.GONE);
            departSpinner.setVisibility(View.VISIBLE);
        }
        if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
            tvUserPermission.setText("实验室安全员");
            permission_check=tvUserPermission.getText().toString();
        } else {
            tvUserPermission.setVisibility(View.GONE);
            permission_spinner.setVisibility(View.VISIBLE);
        }

        List<String> depart_spinner_data = new ArrayList<>();
        depart_spinner_data.add("计算机学院");
        depart_spinner_data.add("深蓝学院");
        depart_spinner_data.add("化工学院");
        depart_spinner_data.add("环境学院");
        depart_spinner_data.add("生物学院");
        depart_spinner_data.add("人文社科学院");
        depart_spinner_data.add("数理学院");


        List<String> permission_spinner_data = new ArrayList<>();
        permission_spinner_data.add("实验室安全员");
        permission_spinner_data.add("学院负责人");
        permission_spinner_data.add("校管理员");

        ArrayAdapter<String> spinner_permission_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, permission_spinner_data);
        ArrayAdapter<String> spinner_depart_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depart_spinner_data);

        permission_spinner.setAdapter(spinner_permission_adapter);

        departSpinner.setAdapter(spinner_depart_adapter);

        departSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
              depart_check = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        permission_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                permission_check = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }


    @OnClick(R.id.add_labs)
    public void onViewClicked() {
        user.setPassword(initPassword.getText().toString());
        user.setJobNumber(jobNumber.getText().toString());
        user.setPermission(CommonUtils.getRoleNumber(permission_check)+"");
        user.setDepartId(commonUtils.getDepartId(depart_check));
        user.setTel(telPhone.getText().toString());
        user.setUsername(addUserName.getText().toString());
        mPresenter.addUser(user);
    }

    @Override
    public void showResult(String msg) {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("SUCCESS")
                .setContentText(msg)
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        jobNumber.setText("");
                        addUserName.setText("");
                        initPassword.setText("");
                        telPhone.setText("");
                    }
                }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
