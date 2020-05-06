package com.example.laboratory.ui.core.model.impl;


import com.example.laboratory.api.ApiServer;
import com.example.laboratory.net.RxRetrofit;
import com.example.laboratory.ui.core.model.IModel;

public class BaseModel implements IModel {

    @Override
    public ApiServer doRxRequest() {
        return RxRetrofit.Api();
    }
}
