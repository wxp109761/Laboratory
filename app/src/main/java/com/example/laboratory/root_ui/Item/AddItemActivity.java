package com.example.laboratory.root_ui.Item;

import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends BasePresenterActivity<ItemListPresenter> implements ItemListContact.ItemListView {


    @BindView(R.id.item_name)
    EditText itemName;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.depart_spinner)
    Spinner departSpinner;
    @BindView(R.id.add_labs)
    Button addLabs;


    String depart_check="";
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("新增待巡检项目");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_item;
    }

    @Override
    protected ItemListPresenter createPresenter() {
        return new ItemListPresenter();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
            tvDepart.setText(CommonUtils.getDepartName(UserInfoManager.getUserInfo().getDepartId()));
            depart_check=tvDepart.getText().toString();
        } else {
            tvDepart.setVisibility(View.GONE);
            departSpinner.setVisibility(View.VISIBLE);
        }


        List<String> depart_spinner_data = new ArrayList<>();
        depart_spinner_data.add("公共项目");
        depart_spinner_data.add("计算机学院");
        depart_spinner_data.add("深蓝学院");
        depart_spinner_data.add("化工学院");
        depart_spinner_data.add("环境学院");
        depart_spinner_data.add("生物学院");
        depart_spinner_data.add("人文社科学院");
        depart_spinner_data.add("数理学院");
        ArrayAdapter<String> spinner_depart_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depart_spinner_data);

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

    }

    @Override
    public void showResult(String result) {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("SUCCESS")
                .setContentText(result)
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        itemName.setText("");
                    }
                }).show();

    }

    @Override
    public void setData(List<Items.ItemListBean> data) {

    }

    @Override
    public List<Items.ItemListBean> getData() {
        return null;
    }

    @Override
    public void showContent() {

    }

    @OnClick(R.id.add_labs)
    public void onViewClicked() {
        Items.ItemListBean itemListBean = new Items.ItemListBean();
        itemListBean.setItemname(itemName.getText().toString());

        itemListBean.setBelong(CommonUtils.getDepartId(depart_check));


        mPresenter.addItems(itemListBean);
    }


}
