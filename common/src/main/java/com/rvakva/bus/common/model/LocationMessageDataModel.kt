package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.model.IModel
import com.rvakva.travel.devkit.model.LocationModel
import com.rvakva.travel.devkit.model.UserInfoModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:49
 */
class LocationMessageDataModel(
    val appKey: String = Ktx.getInstance().appKeyDataSource.appKey,
    val driver: UserInfoModel? = null,
    val location: LocationModel? = null
) : IModel