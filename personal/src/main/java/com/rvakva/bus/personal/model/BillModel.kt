package com.rvakva.bus.personal.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午3:16
 */
class BillModel(
    /**
     * 流水类型 1支出  2收入
     */
    val type: Int = 0,
    /**
     * 金额
     */
    val money: Double = 0.0,
    /**
     * 车牌号
     */
    val carNumber: String = "",
    /**
     * 座位数
     */
    val seatNumber: Int = 0,
    /**
     * 创建时间
     */
    val createTime: Long = 0


) : IModel