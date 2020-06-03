package com.rvakva.bus.home

import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.travel.devkit.mqtt.MqttConfigModel
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult
import retrofit2.http.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午1:53
 */
interface HomeService {

    /**
     *
     */
    @POST("api/v1/common/captcha/send/sms")
    @FormUrlEncoded
    suspend fun sendSms(
        @Field("phone") phone: String,
        @Field("random") random: String,
        @Field("type") type: String,
        @Field("userType") userType: Int,
        @Field("inviteCode") inviteCode: String
    ): BaseResult?


    /**
     * 上下班
     */
    @PUT("api/v1/driver/info/changeStatus")
    @FormUrlEncoded
    suspend fun changeStatus(
        @Field("status") status: Int
    ): BaseResult?

    /**
     * 新班次获取
     */
    @GET("api/v1/driver/order")
    suspend fun getOrderList(
        @Query("driverId") driverId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("status") status: Int
    ): EmResult<List<ScheduleDataModel>>?

    /**
     * 查询mqtt配置
     */
    @GET("api/v1/common/mqtt_config")
    suspend fun getMqttConfig(): EmResult<MqttConfigModel>?

    /**
     * 根据班次id查询班次详情
     */
    @GET("api/v1/driver/order/detail/{orderDriverId}")
    suspend fun queryScheduleById(@Path("orderDriverId") orderDriverId: Long) : EmResult<ScheduleDataModel>?
}