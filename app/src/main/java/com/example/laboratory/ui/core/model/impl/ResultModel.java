package com.example.laboratory.ui.core.model.impl;

import com.example.laboratory.bean.Result;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.RxSchedulers;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.IResultModel;

public class ResultModel extends CommonModel implements IResultModel {
    @Override
    public void getXjResult(String xjid, RxObserver<Result> rxObserver) {
        doRxRequest()
                .getResultByXjId("Bearer "+ UserInfoManager.getUserInfo().getToken(),xjid)
                .compose(RxSchedulers.<Result>io_main())
                .subscribe(rxObserver);
    }
}
