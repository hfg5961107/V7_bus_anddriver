package com.rvakva.bus.personal.model


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         孙艺
 * @CreateDate:     2020/6/24 上午10:38
 */
class FinanceModel (

    /**
     * 订单id
     **/
    var orderId: Long = 0,
    /**
     * 订单编号
     */
    var orderNo: String = "",
    /**
     * 班次信息 线路出发时间
     */
    var startLineTime: String = "",
    /**
     * 班次信息 线路结束时间
     */
    var endLineTime: String = "",
    /**
     * 排班时间
     */
    var schedulingTime: String = "",
    /**
     * 完成时间
     */
    val created: Long = 0,
    /**
     * 车牌号
     */
    val licenseNo: String = "",
    /**
     * 车辆座位数 5/7   初始化5座
     */
    val vehicleSeat: Int = 5,
    /**
     * 购票人数  初始化1人
     */
    val passengerNum: Int = 1,


    /**
     * 乘客联系人
     */
    val passengerName: String = "",
    /**
     * 上车地点
     */
    val startStationName: String = "",
    /**
     * 下车地点
     */
    val endStationName: String = "",


    /**
     * 车票价格
     */
    val orderFee: Double = 0.0,
    /**
     * 优惠券折扣
     */
    val couponFee: Double = 0.0,
    /**
     * 实际支付
     */
    val realFee: Double = 0.0,
    /**
     * 提成比例
     */
    val proportion: Double = 0.0,
    /**
     * 司机收入
     */
    val driverIncome: Double = 0.0


)