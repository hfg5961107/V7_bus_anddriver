package com.rvakva.bus.common

import com.rvakva.bus.common.model.AppInfoModel
import com.rvakva.bus.common.model.UserTokenModel
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.model.UserStatisticsModel
import com.rvakva.travel.devkit.retrofit.result.EmResult
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:20
 */
interface CommonService {

    /**
     * 充值
     */
    @POST("api/v1/driver/info/recharge")
    @FormUrlEncoded
    suspend fun recharge(
        @Field("channel") channel: String,
        @Field("fee") fee: String
    ): EmResult<Any>?

    /**
     * 获取用户信息
     */
    @GET("api/v1/driver/info")
    suspend fun getUserInfo(): EmResult<UserInfoModel>?
    /**
     * 获取用户统计数据
     */
    @GET("api/v1/driver/statistics")
    suspend fun getUserStatistics(): EmResult<UserStatisticsModel>

    /**
     * 获取用户余额及其限制
     */
    @GET("api/v1/driver/info/balance")
    suspend fun getUserConfig(): EmResult<UserConfigModel>?

    /**
     * 登陆
     */
    @POST("api/v1/driver/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): EmResult<UserTokenModel>?

    /**
     * 注册
     */
    @POST("api/v1/driver/info/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("random") random: String,
        @Field("smsCode") smsCode: String,
        @Field("inviteCode") inviteCode: String
    ): EmResult<UserTokenModel>?

    /**
     * 下载apk
     */
    @Streaming
    @GET
    suspend fun download(@Url url: String): ResponseBody

    /**
     * 获取最新版app信息
     */
    @GET("api/v1/driver/info/app")
    suspend fun getAppInfo(@Query("appVersionCode") appVersionCode: Int): EmResult<AppInfoModel>?
}