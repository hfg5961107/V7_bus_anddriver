package com.rvakva.bus.home

import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.model.CompleteModel
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
     *发送短信
     */
    @POST("api/v1/common/captcha/send/sms/heavy")
    @FormUrlEncoded
    suspend fun sendSms(
        @Field("phone") phone: String,
        @Field("random") random: String,
        @Field("type") type: String,
        @Field("userType") userType: Int
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
    suspend fun queryScheduleById(@Path("orderDriverId") orderDriverId: Long): EmResult<ScheduleDataModel>?

    /**
     * 站点检票
     */
    @PUT("api/v1/driver/order/check")
    @FormUrlEncoded
    suspend fun checkTicket(
        @Field("orderCheck") orderCheck: String
        ,@Field("orderDriverId") orderDriverId: Long
    ): EmResult<CompleteModel>?

    /**
     * 开始行程/送人
     */
    @PUT("api/v1/driver/order/gotoDestination")
    @FormUrlEncoded
    suspend fun gotoDestination(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderId") orderId: Long?
    ): EmResult<CompleteModel>?


    /**
     * 完成订单/完成班次
     */
    @PUT("api/v1/driver/order/finish")
    @FormUrlEncoded
    suspend fun finishOrder(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderId") orderId: Long?
    ): EmResult<CompleteModel>?

    /**
     * 查询接人送人顺序
     */
    @GET("api/v1/driver/order/sort")
    suspend fun qureyOrderList(
        @Query("orderDriverId") orderDriverId: Long
    ): EmResult<MutableList<PassengerModel>>?

    /**
     * 修改订单排序
     */
    @PUT("api/v1/driver/order/sort")
    @FormUrlEncoded
    suspend fun changeOrderList(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderSort") orderSort: String
    ): EmResult<CompleteModel>?

    /**
     * 接人/前往预约地
     */
    @PUT("api/v1/driver/order/gotoBookPlace")
    @FormUrlEncoded
    suspend fun gotoBookPlace(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderId") orderId: Long?
    ): EmResult<CompleteModel>?

    /**
     * 到达预约地
     */
    @PUT("api/v1/driver/order/arriveBookPlace")
    @FormUrlEncoded
    suspend fun arriveBookPlace(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderId") orderId: Long?
    ): EmResult<CompleteModel>?


    /**
     * 乘客上车或者跳过
     */
    @PUT("api/v1/driver/order/takeOverCheck")
    @FormUrlEncoded
    suspend fun takeOverCheck(
        @Field("driverId") driverId: Long
        ,@Field("orderDriverId") orderDriverId: Long
        ,@Field("orderId") orderId: Long
        ,@Field("loadType") loadType: Int
    ): EmResult<CompleteModel>?



}