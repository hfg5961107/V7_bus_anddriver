package com.rvakva.travel.devkit.retrofit.interceptor

import com.rvakva.travel.devkit.retrofit.progress.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午3:21
 */
class ProgressInterceptor(
    private val block: (String?, Long, Long, Boolean) -> Unit
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return originalResponse.body?.let {
            originalResponse.newBuilder()
                .body(
                    ProgressResponseBody(
                        it,
                        chain.request().url.toString(),
                        block
                    )
                )
                .build()
        } ?: originalResponse
    }

}