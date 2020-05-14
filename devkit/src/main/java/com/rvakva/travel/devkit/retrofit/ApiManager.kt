package com.rvakva.travel.devkit.retrofit

import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.model.ProgressModel
import com.rvakva.travel.devkit.retrofit.interceptor.ProgressInterceptor
import com.rvakva.travel.devkit.retrofit.interceptor.TokenInterceptor
import com.sherloki.devkit.retrofit.sslfactory.Ssl
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.linjiang.pandora.Pandora
import java.io.File
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/7 下午6:53
 */
class ApiManager private constructor() {

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        var INSTANCE = ApiManager()
    }

    private val DEFAULT_TIMEOUT = 15_000L

    fun initialize() {
        retrofitInstance = Retrofit
            .Builder()
            .client(
                OkHttpClient.Builder()
                    .cache(Cache(File(Ktx.getInstance().app.cacheDir, "cache"), 1024 * 1024 * 2))
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .apply {
                        Ssl().let {
                            it.sslSocketFactory?.let { factory ->
                                it.trustManager?.let { manager ->
                                    sslSocketFactory(factory, manager)
                                }
                            }
                        }
                    }
                    .addInterceptor(TokenInterceptor())
                    .addInterceptor(Pandora.get().interceptor)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor(
                        ProgressInterceptor { url: String?, bytesRead: Long, contentLength: Long, done: Boolean ->
                            url?.let {
                                if (contentLength != -1L) {
                                    KtxViewModel.progressLiveData.postEventValue(
                                        ProgressModel(
                                            it,
                                            (((100 * bytesRead) / contentLength).toInt())
                                        )
                                    )
                                }
                            }
                        }
                    )
                    .build()
            )
            //添加一个Gson转化
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.BASE_URL)
            .build()
    }

    private var retrofitInstance: Retrofit? = null

    fun <T> createService(clazz: Class<T>): T =
        retrofitInstance?.create(clazz)
            ?: throw NullPointerException("Have You initialize Retrofit")
}
