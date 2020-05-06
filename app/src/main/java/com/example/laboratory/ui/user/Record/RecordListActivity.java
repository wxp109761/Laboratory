package com.example.laboratory.ui.user.Record;

import com.example.laboratory.R;
import com.example.laboratory.bean.Record;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.RecordListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import java.util.List;

public class RecordListActivity extends BaseAbListActivity<RecordPresenter, Record.RecordListBean> implements RecordContract.IRecordView, RecordListAdapter.OnRecordListItemClickListener {

    int position;
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.xj_records);
        return true;
    }
    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.loadRecordList();
    }



    @Override
    public void setData(List<Record.RecordListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    @Override
    protected BaseListAdapter<Record.RecordListBean> getListAdapter() {
        return new RecordListAdapter(this);
    }


    @Override
    public void showResult(String msg) {

    }


    @Override
    public void onDeleteClick(int position, String xjid) {
        mPresenter.deleteXjRecordByXjId(xjid);
        mListData.remove(position);
        if(mListData.size()==0){
            showEmpty();
        }else {
            mRecyclerView.notifyDataSetChanged();
        }
    }



}
