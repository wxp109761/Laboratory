package com.example.laboratory.root_ui.UserInfoList;

import android.content.Intent;
import android.os.Bundle;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.inter.OnUserInfoItemClickListener;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.adapter.UserInfoListAdapter;
import com.example.laboratory.root_ui.message.SendMessage;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListFragment;

import java.util.List;


public class UserListFragment extends BaseAbListFragment<UserInfoListPresenter, UserList.UserListBean> implements UserInfoListContact.IUserInfoListView , OnUserInfoItemClickListener {


    @Override
    protected void loadDatas() {
        mPresenter.getUserExceptSelf(UserInfoManager.getUserInfo().getUid());
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
         mListData.addAll(data);
    }


    @Override
    public void onItemClick(int position, UserList.UserListBean bean) {
        Intent intent = new Intent(getContext(), SendMessage.class);
        Bundle bundle = new Bundle();
        bundle.putString("receive_id",bean.getUid());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showResult(String msg) {

    }
}
