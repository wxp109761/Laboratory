package com.example.laboratory.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.laboratory.R;
import com.example.laboratory.bean.Remind;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.Remind.RemindContract;
import com.example.laboratory.ui.Remind.RemindPresenter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListFragment;
import com.example.laboratory.ui.base.BasePresenterFragment;


import java.util.ArrayList;
import java.util.List;

import static com.example.laboratory.R2.id.recyclerView;


public class RemindListFragment extends BaseAbListFragment<RemindPresenter, Remind.RemindListBean> implements RemindContract.IRemindView, TodoRecyclerViewAdapter.OnDeleteRemindListener {
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected RemindPresenter createPresenter() {
        return new RemindPresenter();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    TodoRecyclerViewAdapter adapter;
    @Override
    protected void initViews(View view) {
        super.initViews(view);
       adapter = new TodoRecyclerViewAdapter(this,this.getContext(),mListData);
        ItemTouchHelper.Callback callback = new TodoItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void showResult(String msg) {
        if(msg.equals("删除成功")){
            Log.d("DDDD",msg+"ccc");
            if(mListData.size()==0){
                showEmpty();
            }else {
                mRecyclerView.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDeleteRemindListener(Integer id) {

        mPresenter.deleteRemind(id);

    }



    @Override
    public void setData(List<Remind.RemindListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }





    @Override
    protected void loadDatas() {
        mPresenter.getRemindsByUid(UserInfoManager.getUserInfo().getUid());
    }

    @Override
    protected BaseListAdapter<Remind.RemindListBean> getListAdapter() {
        return adapter;
    }
}
