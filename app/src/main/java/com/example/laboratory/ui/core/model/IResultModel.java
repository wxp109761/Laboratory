package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Result;
import com.example.laboratory.net.callback.RxObserver;

public interface IResultModel {
    void getXjResult(String zjid, RxObserver<Result> rxObserver);
}
