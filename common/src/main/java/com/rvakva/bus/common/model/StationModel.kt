package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/3 下午6:09
 */
class StationModel(
    /**
     * id
     */
    val id: Long = 0,
    /**
     * 地址
     */
    val address : String?,
    /**
     * 经度
     */
    val latitude : Double = 0.0,
    /**
     * 纬度
     */
    val longitude: Double = 0.0,
    /**
     * 站点类型  1是起点 2是终点
     */
    val type : Int = 0,
    /**
     * 顺序
     */
    val sort : Int = 0,
    /**
     * 订单id
     */
    val orderId : Long = 0
) : IModel