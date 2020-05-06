package com.example.laboratory.net.callback;

import com.example.laboratory.bean.BaseBean;
import com.example.laboratory.net.NetConfig;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * author: 位展朋
 * date: 2020.02.24
 */

public abstract class RxConsumer<T> implements Consumer<BaseBean<T>> {

    @Override
    public void accept(@NonNull BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.code == NetConfig.REQUEST_SUCCESS){
            onSuccess(tBaseBean.data);
        }else {
            onFail(tBaseBean.message);
        }
    }

    protected abstract void onFail(String errorMsg);

    protected abstract void onSuccess(T data);
}