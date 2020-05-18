package com.example.laboratory.ui.result;

import com.example.laboratory.bean.Result;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.ResultModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;



public class ResultPresenter extends BasePresenter<ResultContract.IResultView> implements ResultContract.IResultPresenter {
    private ResultContract.IResultView mResultView;
    private ResultModel mResultModel;

    ResultPresenter() {
        this.mResultModel = new ResultModel();
    }

    @Override
    public void loadResult() {
        mResultView = getView();
        RxObserver<Result> mResultList=new RxObserver<Result>(this) {
            @Override
            protected void onSuccess(Result data) {
                List<Result.ResultListBean> bean=data.getResultList();
                mResultView.setData(bean);
                if (mResultView.getData().size() == 0)
                    mResultView.showEmpty();
                else
                    mResultView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mResultView.showFail(errorMsg);
            }
        };
        mResultModel.getXjResult(mResultView.getXjd(),mResultList);
        addDisposable(mResultList);
    }



}
