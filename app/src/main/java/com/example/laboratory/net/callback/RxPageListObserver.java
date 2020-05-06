package com.example.laboratory.net.callback;

import com.example.laboratory.bean.BaseBean;
import com.example.laboratory.bean.PageListData;
import com.example.laboratory.net.NetConfig;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IPageLoadDataView;

import java.util.List;

/**
 * 分页加载功能的接口回调类
 * 分页加载逻辑在这里统一处理
 * author: 康栋普
 * date: 2018/3/13
 */

public abstract class RxPageListObserver<T> extends RxBaseObserver<PageListData<T>> {

    private IPageLoadDataView mListDataView;

    public RxPageListObserver(BasePresenter mPresenter) {
        super(mPresenter);
        this.mListDataView = (IPageLoadDataView) mPresenter.getView();
    }
    @Override
    public void onNext(BaseBean<PageListData<T>> baseBean) {
        if (baseBean.code == NetConfig.REQUEST_SUCCESS) {

            PageListData<T> mListData = baseBean.data;
            if (mListDataView.getPage() == mListDataView.getFirstPage()) {
                mListDataView.clearListData();
            }
            if (mListData.isOver()) {
                mListDataView.showNoMore();
            } else {
                mListDataView.autoLoadMore();
            }
            onSuccess(mListData.getDatas());
        } else {
            onFail(baseBean.code, baseBean.message  );
        }
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        mListDataView.showError();
    }

    public abstract void onSuccess(List<T> mData);

    public abstract void onFail(int errorCode, String errorMsg);
}
