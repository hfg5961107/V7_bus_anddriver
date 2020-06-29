package com.rvakva.travel.devkit.model

import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:49
 */
class LocationMessageDataModel(
//    val appKey: String = Ktx.getInstance().appKeyDataSource.appKey,
    var driverId : Long = 0,
    var driverPhone : String? = null,
    var driverName : String? = null,
    val location: LocationModel? = null
)