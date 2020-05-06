package com.example.laboratory.ui.core.model;

import com.example.laboratory.api.ApiServer;


public interface IModel {
    /**
     * 使用RxRetrofit请求数据
     *
     * @return
     */
    ApiServer doRxRequest();

}
