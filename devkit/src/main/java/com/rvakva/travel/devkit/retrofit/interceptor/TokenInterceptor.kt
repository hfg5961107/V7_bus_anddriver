package com.rvakva.travel.devkit.retrofit.interceptor

import com.rvakva.travel.devkit.Ktx
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:38
 */
class TokenInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "token",
                    Ktx.getInstance().userDataSource.userToken
                ).addHeader(
                    "appKey",
                    "123456"
                )
                .build()
        )
    }
}