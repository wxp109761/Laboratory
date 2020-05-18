package com.example.laboratory.net.callback;

import com.example.laboratory.bean.BaseBean;
import com.example.laboratory.net.NetConfig;

import io.reactivex.Observable;
import io.reactivex.functions.Function;



public abstract class RxFunction<T, R> implements Function<BaseBean<T>, Observable<BaseBean<R>>> {

    @Override
    public Observable<BaseBean<R>> apply(BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.code == NetConfig.REQUEST_SUCCESS){
            return doOnNextRequest();
        }
        return null;
    }

    protected abstract Observable<BaseBean<R>> doOnNextRequest();

}
