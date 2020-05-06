package com.example.laboratory.root_ui.UserInfoList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.laboratory.R;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.inter.OnUserInfoItemClickListener;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.Dialog.filterDialog;
import com.example.laboratory.root_ui.adapter.UserInfoListAdapter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;
import com.example.laboratory.ui.user.UserInfoActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInfoListActivity extends BaseAbListActivity<UserInfoListPresenter, UserList.UserListBean> implements UserInfoListContact.IUserInfoListView , OnUserInfoItemClickListener,filterDialog.OnFilterSelectListener {


    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            return false;
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
                filterDialog filterDialog=new filterDialog(this,this,departName,categorys,"user");
                filterDialog.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void loadDatas() {

        mPresenter.getUserListPermissionNot("0");
    }

    @Override
    protected BaseListAdapter<UserList.UserListBean> getListAdapter() {
        return new UserInfoListAdapter(this);
    }

    @Override
    protected UserInfoListPresenter createPresenter() {
        return new UserInfoListPresenter();
    }

    @Override
    public void setData(List<UserList.UserListBean> data) {
         mListData.clear();
        if(UserInfoManager.getUserInfo().getPermission().equals("0")){
            for (int i = 0; i <data.size() ; i++) {
                if(data.get(i).getPermission().equals("1"))
                    mListData.add(data.get(i));
            }

        }else{
            for (int i = 0; i <data.size() ; i++) {
                if(data.get(i).getDepartId().equals(UserInfoManager.getUserInfo().getDepartId())&&!data.get(i).getUid().equals(UserInfoManager.getUserInfo().getUid()))
                mListData.add(data.get(i));
            }
        }

    }

    @Override
    protected boolean initToolbar() {
        if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            mToolbar.setTitle("安全员信息列表");
        }else {
            mToolbar.setTitle("院负责人信息列表");
        }

        return true;
    }

    @Override
    public void onItemClick(int position, UserList.UserListBean bean) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        bundle.putString("cate","1");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showResult(String msg) {

    }

    List<UserList.UserListBean> userListBeans =new ArrayList<>();
    String departid;
    @Override
    public void getValue(String departSelect, String cateSelect) {
        userListBeans.clear();
        if(departSelect.equals("不限")){
            userListBeans.addAll(mListData);
        }else {
            departid= CommonUtils.getDepartId(departSelect);
            for (int i = 0; i <mListData.size() ; i++) {
                if(mListData.get(i).getDepartId().equals(departid)){
                    userListBeans.add(mListData.get(i));
                }
            }
        }
        mListData.clear();
        System.out.println("  rrr"+userListBeans.size());
        mListData.addAll(userListBeans);
        if(userListBeans.size()==0){
            showEmpty();
        }else {
            mRecyclerView.notifyDataSetChanged();
        }



    }
}
