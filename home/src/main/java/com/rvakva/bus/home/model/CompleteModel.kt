package com.rvakva.bus.home.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/8 下午6:15
 */
class CompleteModel(
    var serviceTime: Long = 0,
    var totalTime: String?,
    var isFinish: Boolean = false
) : IModel