package com.rvakva.bus.common.extent

import com.rvakva.bus.common.model.LocationMessageDataModel
import com.rvakva.bus.common.model.LocationMessageModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.model.LocationModel
import com.rvakva.travel.devkit.model.UserInfoModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:48
 */
fun LocationModel.createLocationMessage(
    userInfo: UserInfoModel
) = com.google.gson.Gson().toJson(
    LocationMessageModel(
        data = LocationMessageDataModel(
            driver = userInfo,
            location = this
        )
    )
)

fun String?.getImageUrl() = "${Config.IMAGE_SERVER}${this}"

