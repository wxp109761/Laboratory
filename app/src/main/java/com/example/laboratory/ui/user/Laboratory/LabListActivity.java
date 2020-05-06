package com.example.laboratory.ui.user.Laboratory;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.refactor.lib.colordialog.ColorDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.Dialog.filterDialog;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.LaboratoryListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabListActivity extends BaseAbListActivity<LabPresenter, LaboratoryList.LabListBean> implements LabContract.ILabView,LaboratoryListAdapter.OnLabListItemClickListener,filterDialog.OnFilterSelectListener {

    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(lab_cate.equals("mine")){
            return  false;
        }
        getMenuInflater().inflate(R.menu.right_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_notice:
                refreshData();
                List<String> departName=new ArrayList<>();
                List<String> categorys=new ArrayList<>();
                departName.add("不限");
                categorys.add("不限");
                departName.addAll(Arrays.asList(getResources().getStringArray(R.array.departNames)));

                categorys.addAll(Arrays.asList(getResources().getStringArray(R.array.labCates)));
                filterDialog filterDialog=new filterDialog(this,this,departName,categorys,"laboratory");
                filterDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    List<LaboratoryList.LabListBean> labListBeans=new ArrayList<>();
    String departid;
    @Override
    public void getValue(String departSelect, String cateSelect) {
        labListBeans.clear();
        System.out.println("jjj"+departSelect+"  "+cateSelect);
        if(departSelect.equals("不限")){
            if(cateSelect.equals("不限")){
                labListBeans.addAll(mListData);
            }else {
                for (int i = 0; i <mListData.size() ; i++) {
                    if(mListData.get(i).getCategory().equals(cateSelect)){
                        labListBeans.add(mListData.get(i));
                    }
                }
                System.out.println("zzz"+departSelect+"  "+cateSelect);
            }
        }else {
           departid=CommonUtils.getDepartId(departSelect);
            if(cateSelect.equals("不限")){
                for (int i = 0; i <mListData.size() ; i++) {
                    if(mListData.get(i).getDepartId().equals(departid)){
                        labListBeans.add(mListData.get(i));
                    }
                }
                System.out.println("qqq"+departid+"  "+cateSelect);
            }else {
                for (int i = 0; i <mListData.size() ; i++) {
                    if(mListData.get(i).getDepartId().equals(departid)&&mListData.get(i).getCategory().equals(cateSelect)){
                        labListBeans.add(mListData.get(i));
                    }
                }
                System.out.println("aaa"+departid+"  "+cateSelect);
            }
        }

        mListData.clear();
        mListData.addAll(labListBeans);

        if(labListBeans.size()==0){
            showEmpty();
        }else {
            mRecyclerView.notifyDataSetChanged();
        }

    }

    String lab_cate;

    @Override
    protected LabPresenter createPresenter() {
        return new LabPresenter();
    }

    @Override
    protected boolean initToolbar() {
        Bundle bundle = this.getIntent().getExtras();
        lab_cate = bundle.getString("mine");
        if (lab_cate.equals("mine")) {
            mToolbar.setTitle("我的实验室");
        } else {
            mToolbar.setTitle("实验室管理");
        }
        return true;
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

    @Override
    protected void loadDatas() {

        if (lab_cate.equals("mine")) {
            mPresenter.getLabsData(UserInfoManager.getUserInfo().getUid(), "uid");
        } else {
            if (UserInfoManager.getUserInfo().getPermission().equals("0")) {
                mPresenter.getLabsData("", "all");
            } else if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
                mPresenter.getLabsData(UserInfoManager.getUserInfo().getDepartId(), "depart_id");
            }
        }
    }


    @Override
    protected BaseListAdapter<LaboratoryList.LabListBean> getListAdapter() {
        return new LaboratoryListAdapter(this,lab_cate);
    }



    @Override
    public void setData(List<LaboratoryList.LabListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    @Override
    public void onDeleteLabsClick(int position, String labid) {
        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("提醒");
        dialog.setContentText("你确定要删除该实验室及实验室对应待检测项目关系及该实验室的巡检记录及结果集吗");
        dialog.setPositiveListener("确定", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                mPresenter.deleteLabById(labid);
                mListData.remove(position);
                if(mListData.size()==0){
                    showEmpty();
                }else {
                    mRecyclerView.notifyDataSetChanged();
                }
                                       dialog.dismiss();
            }
        })
                .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    public void showResult(String msg) {

    }


}
