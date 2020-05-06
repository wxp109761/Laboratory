package com.example.laboratory.net.callback;

import com.example.laboratory.application.AppContext;
import com.example.laboratory.net.NetExceptionHandle;

import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;
import io.reactivex.observers.DisposableObserver;

public abstract class RxZipObserver<T> extends DisposableObserver<T> {
    protected IView view;

    protected RxZipObserver(BasePresenter mPresenter) {
        this.view = mPresenter.getView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //显示loading
        showLoading();
    }


    @Override
    public void onError(Throwable e) {
        //隐藏loading
        hideLoading();
        //处理异常
        NetExceptionHandle.dealException(AppContext.getContext(),e);
    }

    @Override
    public void onComplete() {
        hideLoading();
    }

    public void showLoading() {
        view.showLoading("");
    }
    private void hideLoading() {
        if (null != view)
            this.view.hideLoading();
    }
}
