package com.rvakva.bus.personal

import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.travel.devkit.retrofit.result.EmResult
import retrofit2.http.*

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



    /**
     * 班次查询
     */
    @GET("api/v1/driver/order")
    suspend fun getOrderList(
        @Query("driverId") driverId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("status") status: Int
    ): EmResult<List<ScheduleDataModel>>?
}