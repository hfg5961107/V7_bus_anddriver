package com.rvakva.bus.home

import com.rvakva.bus.common.model.OrderDataModel
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

    @GET("api/v1/driver/order/new")
    suspend fun getOrderNewList(
//        @Query("orderType") orderType: Int?,
//        @Query("isTip") isTip: Boolean?,
//        @Query("orderByDistance") orderByDistance: Int?
    ): EmResult<List<OrderDataModel>>?

    @GET("/api/v1/driver/order/assign")
    suspend fun getOrderAssignList(
//        @Query("orderType") orderType: Int?,
//        @Query("isTip") isTip: Boolean?,
//        @Query("orderByDistance") orderByDistance: Int?
    ): EmResult<List<OrderDataModel>>?

    @GET("api/v1/driver/order/running")
    suspend fun getOrderRunningList(
//        @Query("orderType") orderType: Int?,
//        @Query("isTip") isTip: Boolean?,
//        @Query("orderByDistance") orderByDistance: Int?
    ): EmResult<List<OrderDataModel>>?
}