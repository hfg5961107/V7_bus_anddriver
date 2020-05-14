package com.rvakva.bus.personal

import com.rvakva.travel.devkit.retrofit.result.EmResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午11:43
 */
interface PersonalService {

    @POST("api/v1/driver/info/cashOut")
    @FormUrlEncoded
    suspend fun cashOut(@Field("fee") fee: String): EmResult<String>?
}