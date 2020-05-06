package com.example.laboratory.common;

import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;

public class CommonrxObserver {
    public static RxObserver<String> rxObserver(BasePresenter basePresenter, IView iView, String msg){
        RxObserver<String> rxObserver=new RxObserver<String>(basePresenter) {
            @Override
            protected void onSuccess(String data) {
                iView.showResult(msg);
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iView.showFail(errorMsg);
            }
        };
        return rxObserver;
    }

}
