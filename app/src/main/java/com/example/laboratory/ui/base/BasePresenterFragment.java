package com.example.laboratory.ui.base;

import android.os.Bundle;

import android.view.View;
import androidx.annotation.Nullable;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;


/**
 * author: 康栋普
 * date: 2018/2/11
 */

public abstract class BasePresenterFragment<P extends BasePresenter> extends BaseFragment implements IView{

    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        //关联View
        attachView();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除关联
        detachView();
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.removeAllDisposable();
            mPresenter = null;
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract P createPresenter();

    @Override
    public void showLoading(String msg) {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showFail(String msg) {
    }

    @Override
    public void showError() {
    }

    @Override
    public void showEmpty() {
    }
}
