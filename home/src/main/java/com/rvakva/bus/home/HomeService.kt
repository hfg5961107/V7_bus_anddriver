package com.rvakva.bus.home

import com.rvakva.travel.devkit.retrofit.result.BaseResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午1:53
 */
interface HomeService {

    @POST("api/v1/common/captcha/send/sms")
    @FormUrlEncoded
    suspend fun sendSms(
        @Field("phone") phone: String,
        @Field("random") random: String,
        @Field("type") type: String,
        @Field("userType") userType: Int,
        @Field("inviteCode") inviteCode: String
    ): BaseResult?


    @PUT("api/v1/driver/info/changeStatus")
    @FormUrlEncoded
    suspend fun changeStatus(
        @Field("status") status: Int
    ): BaseResult?
}