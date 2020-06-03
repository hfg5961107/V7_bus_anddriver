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
     * 流水类型 2-收入 3-充值 4-提现
     */
    val type: Int = 0,
    /**
     * 金额
     */
    val fee: Double = 0.0,
    /**
     * 车牌号
     */
    val licenseNo: String = "",
    /**
     * 座位数
     */
    val vehicleSeat: Int = 0,
    /**
     * 创建时间
     */
    val created: Long = 0

) : IModel