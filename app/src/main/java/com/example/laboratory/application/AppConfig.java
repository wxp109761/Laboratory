package com.example.laboratory.application;

import android.content.Context;

import com.example.laboratory.common.UrlConstainer;
import com.example.laboratory.net.RxRetrofit;
import com.example.laboratory.utils.PreUtils;


/**
 * author: 康栋普
 * date: 2018/3/13
 */

public class AppConfig {

    static void init(Context context){
        //初始化网络框架
        RxRetrofit.getInstance().initRxRetrofit(context, UrlConstainer.baseUrl);
        //初始化缓存
        new PreUtils(context);
    }

}
