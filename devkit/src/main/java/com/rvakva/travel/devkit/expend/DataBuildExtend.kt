package com.rvakva.travel.devkit.expend

import com.rvakva.travel.devkit.model.LocationMessageDataModel
import com.rvakva.travel.devkit.model.LocationMessageModel
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
        data = (mutableListOf<LocationMessageDataModel>().let {
           it.add(
               LocationMessageDataModel(
               driverId = userInfo.id,
               driverName = userInfo.name,
               driverPhone = userInfo.phone,
               location = this
           ))
            return@let it
        })
    )
)

fun String?.getImageUrl() = "${Config.IMAGE_SERVER}${this}"

