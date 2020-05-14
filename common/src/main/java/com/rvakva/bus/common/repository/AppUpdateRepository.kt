package com.rvakva.bus.common.repository

import com.rvakva.bus.common.CommonService
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.retrofit.ApiManager

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:55
 */
class AppUpdateRepository {

    suspend fun getAppInfo() =
        ApiManager.getInstance().createService(CommonService::class.java)
            .getAppInfo(Config.VERSION_CODES)
            .requestMap()
}