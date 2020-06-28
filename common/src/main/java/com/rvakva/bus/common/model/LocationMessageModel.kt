package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:50
 */
class LocationMessageModel(
    val msg: String = "location",
    val data: LocationMessageDataModel
) : IModel