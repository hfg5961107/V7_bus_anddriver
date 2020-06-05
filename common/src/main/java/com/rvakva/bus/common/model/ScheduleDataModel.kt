package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:06
 */

enum class ScheduleStatusTypeEnum(val value: Int) {
    SCHEDULE_TYPE_NEW(Config.SCHEDULE_TYPE_NEW),
    SCHEDULE_TYPE_ING(Config.SCHEDULE_TYPE_ING),
    SCHEDULE_TYPE_COMPLETE(Config.SCHEDULE_TYPE_COMPLETE),
    SCHEDULE_TYPE_CANCEL(Config.SCHEDULE_TYPE_CANCEL)
}

class ScheduleDataModel(

    /**
     * 班次关联id
     */
    val id: Long = 0,

    /**
     * 班次id
     */
    val  schedulingId : Long = 0,

    /**
     * 排班状态（10：已指派，40：行程中，45：已完成，60：已取消）
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
     * 3 接人-送人
     * 4 接人-站点
     */
    val lineType: Int = 0,

    /**
     * 车牌号
     */
    val licenseNo : String?,

    /**
     * 车辆座位数
     */
    val vehicleSeat: Int = 0,

    /**
     * 完成时间
     */
    val  finishTime : Long = 0,

    /**
     * 线路单价
     */
    val lineFee : Double = 0.0,
    /**
     * 乘客信息
     */
    val order: MutableList<PassengerModel>,
    /**
     * 班次编号
     */
    val schedulingNo : String?,
    /**
     * 本单收入
     */
    val realTotalFee: Double = 0.0,
    /**
     * 站点信息
     */
    val station : MutableList<StationModel>

) : IModel