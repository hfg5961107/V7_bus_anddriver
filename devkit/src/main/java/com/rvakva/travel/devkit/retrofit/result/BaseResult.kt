package com.rvakva.travel.devkit.retrofit.result

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:43
 */
open class BaseResult(
    var code: Int = 0,
    var msg: String? = "",
    var total: Int = 0
) : IModel