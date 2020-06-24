package com.rvakva.bus.personal

import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.model.BillModel
import com.rvakva.bus.personal.model.FinanceModel
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult
import retrofit2.http.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午11:43
 */
interface PersonalService {

    /**
     * 提现
     */
    @POST("api/v1/driver/info/cashOut")
    @FormUrlEncoded
    suspend fun cashOut(@Field("fee") fee: String): EmResult<String>?

    /**
     * 班次查询
     */
    @GET("api/v1/driver/order")
    suspend fun getOrderList(
        @Query("driverId") driverId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("status") status: String
    ): EmResult<List<ScheduleDataModel>>?

    /**
     * 钱包明细查询
     */
    @GET("api/v1/driver/flowing")
    suspend fun getBillList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): EmResult<List<BillModel>>?




    @POST("api/v1/driver/info/apply")
    @FormUrlEncoded
    suspend fun commitDriverInfo(
        @Field("name") name: String,
        @Field("idCard") idCard: String,
        @Field("idCardFrontPath") idCardFrontPath: String,
        @Field("idCardBackPath") idCardBackPath: String,
        @Field("attachmentPath") attachmentPath: String?
    ): BaseResult?

    /**
     * 司机申请结算
     */
    @POST("/api/v1/driver/settlement")
    suspend fun applyClose (
        @Query("driverId") driverId: Long,
        @Query("fee") fee: String
    ): EmResult<String>?

    /**
     * 钱包明细查询
     * 时间参数和Model还没有处理
     */
    @GET("/api/v1/driver/finance")
    suspend fun getFinance(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): EmResult<List<BillModel>>?

    /**
     * 流水详情
     */
    @GET("api/v1/driver/finance/{orderId}")
    suspend fun getFlowingDetails(
        @Path("orderId") orderId: Int
    ): EmResult<FinanceModel>?

    /**
     * 收入详情
     */
    @GET("api/v1/driver/{orderId}")
    suspend fun getIncomeDetails(
        @Path("orderId") orderId: Int
    ): EmResult<FinanceModel>?
}