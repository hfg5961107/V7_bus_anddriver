package com.rvakva.bus.personal.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午3:16
 */
class BillModel(

    val id:Int=0,
    /**
     * 流水类型 2-收入 3-充值 4-提现
     */
    val type: Int = 0,
    val orderId: Int = 0,//订单ID
    /**
     * 金额
     */
    val fee: Double = 0.0,
    /**
     * 车牌号
     */
    val licenseNo: String = "",
    val rechargeType: String = "",//充值类型
    /**
     * 座位数
     */
    val vehicleSeat: Int = 0,
    val status: Int = 0,//提现状态/审核状态 1-审核中 2-审核通过 3-审核拒绝
    /**
     * 创建时间
     */
    val created: Long = 0

) : IModel