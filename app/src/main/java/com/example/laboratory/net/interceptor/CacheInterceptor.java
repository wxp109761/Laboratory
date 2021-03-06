package com.example.laboratory.net.interceptor;

import com.example.laboratory.application.AppContext;

import java.io.IOException;

import com.example.laboratory.utils.NetWorkUtils;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置缓存策略
 * author: 康栋普
 * date: 2018/5/24
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetWorkUtils.isNetworkConnected(AppContext.getContext())) {
            //有网络时只从网络获取
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }
        Response response = chain.proceed(request);
        //设置有网不缓存
        if (NetWorkUtils.isNetworkConnected(AppContext.getContext())) {
            //有网络时，设置缓存超时为0
            int maxAge = 0;
            response = response.newBuilder()
                    .removeHeader("Pramga")//清除头消息
                    .header("Cache-Control", "public,max-age=" + maxAge)
                    .build();
        }
        return response;
    }
}
