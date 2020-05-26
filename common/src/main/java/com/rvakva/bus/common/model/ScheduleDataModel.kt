package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:06
 */
class ScheduleDataModel(

    val id: Long = 0,
    /**
     * 排班状态（1：新单，5：订单已支付，10：已指派，15：行程中，30：已完成）
     */
    val status: Int = 0,
    /**
     * 上车站点名称
     */
    val startStationName: String?,
    /**
     * 检票/接人
     */
    val startLineTime: String?,
    /**
     * 剩余座位数
     */
    val remainingSeat: Int = 0,
    /**
     * 下车站点名称
     */
    val endStationName: String?,
    /**
     * 预计发车
     */
    val endLineTime: String?,
    /**
     * 排班时间
     * 时间规则/均采用每天凌晨时2020-05-19 00:00
     */
    val schedulingTime: Long = 0,
    /**
     * 乘车人数
     */
    val passengerAllNum: Int = 0,
    /**
     * 线路类型
     * 1 站点-站点
     * 2 站点-送人
     * 3 送人-送人
     * 4 送人-站点
     */
    val lineType: Int = 0

) : IModel