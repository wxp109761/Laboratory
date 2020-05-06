package com.example.laboratory.ui.result;

import android.os.Bundle;
import android.view.View;
import com.example.laboratory.bean.Record;
import com.example.laboratory.bean.Result;
import com.example.laboratory.common.Const;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.ResultListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import java.util.List;

/**
 * 收藏的文章
 * author: 康栋普
 * date: 2018/3/21
 */

public class ResultActivity extends BaseAbListActivity<ResultPresenter, Result.ResultListBean> implements ResultContract.IResultView {
    @Override
    protected ResultPresenter createPresenter() {
        return new ResultPresenter();
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("巡检结果");
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
        mPresenter.loadResult();
    }

    @Override
    protected BaseListAdapter<Result.ResultListBean> getListAdapter() {
        return  new ResultListAdapter();
    }

    @Override
    public String getXjd() {
        Bundle bundle = this.getIntent().getExtras();
        Record.RecordListBean bean= (Record.RecordListBean) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
        return bean.getXjid();
    }

    @Override
    public void setData(List<Result.ResultListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    @Override
    public void showResult(String msg) {

    }
}
