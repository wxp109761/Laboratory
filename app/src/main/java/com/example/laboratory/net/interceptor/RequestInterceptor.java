package com.example.laboratory.net.interceptor;

import com.example.laboratory.application.AppContext;



import java.io.IOException;

import com.example.laboratory.utils.NetWorkUtils;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置请求策略
 * author: 康栋普
 * date: 2018/5/24
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //无网络时从缓存中获取
        if (!NetWorkUtils.isNetworkConnected(AppContext.getContext())) {
            //无网络时,设置超时为30天
            int maxStale = 30 * 24 * 60 * 60;
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .removeHeader("Pragma")
                    .header("Cache-Control", "only-if-cached,max-stale=" + maxStale)
                    .build();
        }
        return chain.proceed(request);
    }
}
