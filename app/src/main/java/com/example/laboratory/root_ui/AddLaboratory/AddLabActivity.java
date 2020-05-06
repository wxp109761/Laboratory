package com.example.laboratory.root_ui.AddLaboratory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.inter.OnProItemCheckListener;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.Dialog.ItemSelectedDialog;
import com.example.laboratory.ui.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddLabActivity extends BasePresenterActivity<AddLabPresenter> implements AddLabContact.IAddLabsView, OnProItemCheckListener {


    List<String> lab_state_data = new ArrayList<>();
    List<String> lab_safe_status_data = new ArrayList<>();

    List<String> depart_spinner_data = new ArrayList<>();
    List<String> lab_category_data = new ArrayList<>();
    List<String> lab_user_data = new ArrayList<>();
    ArrayAdapter<String> spinner_lab_category_adapter;
    ArrayAdapter<String> spinner_lab_state_adapter;
    ArrayAdapter<String> spinner_lab_safe_status_adapter;

    ArrayAdapter<String> spinner_lab_user_adapter;
    String state_check;
    String safe_status_check;
    String category_check;
    String uid_check;

    List<Items.ItemListBean> itemListBeans = new ArrayList<>();
    List<Items.ItemListBean> mChData;

    String depart_check ="";
    @BindView(R.id.lab_label)
    EditText labLabel;
    @BindView(R.id.lab_state_spinner)
    Spinner labStateSpinner;
    @BindView(R.id.add_lab_function)
    EditText addLabFunction;
    @BindView(R.id.safe_status_spinner)
    Spinner safeStatusSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.uid_spinner)
    Spinner uidSpinner;
    @BindView(R.id.lab_depart)
    TextView labDepart;
    @BindView(R.id.depart_spinner)
    Spinner departSpinner;
    @BindView(R.id.item_list_select)
    TextView itemListSelect;
    @BindView(R.id.add_labs)
    Button addLabs;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_laboratory;
    }


    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("新增实验室");
        return true;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle); mPresenter.getItemsData(CommonUtils.getDepartId(depart_check));

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        List<UserList.UserListBean> uListBean = (List<UserList.UserListBean>) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);

        for (int i = 0; i <uListBean.size() ; i++) {
            lab_user_data.add(uListBean.get(i).getUsername()+ "-" + uListBean.get(i).getUid());
        }


        lab_state_data.add("运行");
        lab_state_data.add("建设中");
        lab_safe_status_data.add("安全");
        lab_safe_status_data.add("存在隐患");

        lab_category_data = Arrays.asList(getResources().getStringArray(R.array.labCates));


        depart_spinner_data = Arrays.asList(getResources().getStringArray(R.array.departNames));

        ArrayAdapter<String> spinner_depart_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depart_spinner_data);
        departSpinner.setAdapter(spinner_depart_adapter);
        spinner_lab_state_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lab_state_data);
        spinner_lab_safe_status_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lab_safe_status_data);
        spinner_lab_category_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lab_category_data);
        categorySpinner.setAdapter(spinner_lab_category_adapter);
        safeStatusSpinner.setAdapter(spinner_lab_safe_status_adapter);
        labStateSpinner.setAdapter(spinner_lab_state_adapter);

        if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
            labDepart.setText(CommonUtils.getDepartName(UserInfoManager.getUserInfo().getDepartId()));
            depart_check = labDepart.getText().toString();

        } else {
            labDepart.setVisibility(View.GONE);
            departSpinner.setVisibility(View.VISIBLE);
            depart_check=departSpinner.getSelectedItem().toString();
            Log.d("Xx",depart_check);
        }


        //  CommonUtils.Sprinner(mc);
//        String check=CommonUtils.Sprinner(this,lab_state_data,labState);
//        CommonUtils.Sprinner(this,lab_safe_status_data,labSafeStatus);
//        CommonUtils.Sprinner(this,lab_category_data,labCategory);


        departSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                depart_check = parent.getItemAtPosition(position).toString();
                mPresenter.getItemsData(CommonUtils.getDepartId(depart_check));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        labStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                state_check = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                category_check = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        safeStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                safe_status_check = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        spinner_lab_user_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lab_user_data);
        uidSpinner.setAdapter(spinner_lab_user_adapter);
        uidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               uid_check= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }


    @Override
    protected AddLabPresenter createPresenter() {
        return new AddLabPresenter();
    }



    @Override
    public void setItemList(List<Items.ItemListBean> itemList) {
        this.itemListBeans = itemList;
    }




    @Override
    public void getCheckData(List<Items.ItemListBean> mCheckData) {
        this.mChData = mCheckData;
        itemListSelect.setText("");
        for (int i = 0; i < mCheckData.size(); i++) {
            itemListSelect.setText(mCheckData.get(i).getItemid() + "," + itemListSelect.getText());
        }
    }

    View addItemView;
    @Override
    public void onClick(View view, String item_name,boolean isChecked) {
        this.addItemView=view;
        switch (view.getId()) {
            case R.id.add_item_btn:
                Items.ItemListBean itemListBean = new Items.ItemListBean();
                if(isChecked){
                    itemListBean.setBelong("0");
                }else {
                    itemListBean.setBelong(CommonUtils.getDepartId(depart_check));
                }
                itemListBean.setItemname(item_name);
                mPresenter.addItems(itemListBean);
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object bean) {

    }

    @Override
    public void showFail(String msg) {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                .setAnimationEnable(true)
                .setTitleText("FILE")
                .setContentText(msg)
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        labLabel.setText("");
                        addLabFunction.setText("");
                        itemListSelect.setText("");
                        mChData.clear();
                    }
                }).show();
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
                        if(msg.equals("条目增加成功")){
                            EditText editText=addItemView.getRootView().findViewById(R.id.add_item_name);
                            editText.setText("");
                        }else{
                            labLabel.setText("");
                            addLabFunction.setText("");
                            itemListSelect.setText("");
                            if(mChData!=null)
                                mChData.clear();
                        }


                    }
                }).show();
    }

    @OnClick({R.id.item_list_select, R.id.add_labs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_list_select:


                RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
                ItemSelectedDialog dialog = new ItemSelectedDialog(this, itemListBeans);
                dialog.show();
                dialog.OnProItemCheckListener(this);


                break;
            case R.id.add_labs:
                Laboratory.RowsBean lab = new Laboratory.RowsBean();
                String lab_id=UUID.randomUUID().toString();
                lab.setLabid(lab_id);
                lab.setLabel(labLabel.getText() + "");
                lab.setState(state_check);
                lab.setFunction(addLabFunction.getText() + "");
                lab.setCategory(category_check);
                lab.setLabUid(UserInfoManager.getUserInfo().getUid());
                lab.setDepartId(CommonUtils.getDepartId(depart_check));
                lab.setSafeStatus(safe_status_check);
                String uid=uid_check.split("-")[1];
                lab.setLabUid(uid);
                if(mChData!=null){
                    for (int i = 0; i < mChData.size(); i++) {
                        mPresenter.addLabItemRelations(lab_id, mChData.get(i).getItemid());
                    }
                }
                mPresenter.addLabs(lab);
                break;

        }
    }
}
