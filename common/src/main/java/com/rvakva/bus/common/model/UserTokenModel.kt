package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:53
 */
class UserTokenModel(
    val token: String? = null,
    val appKey: String? = null
) : IModel