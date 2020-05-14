package com.rvakva.travel.devkit.model

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:53
 */
class UserStatisticsModel(
    var dayOrderCount:Int,
    var dayOrderProfit:Double,
    var monthOrderCount:Int,
    var monthOrderProfit:Double
):IModel