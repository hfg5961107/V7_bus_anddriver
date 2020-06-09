package com.rvakva.bus.common.model

import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.model.IModel
import java.io.Serializable

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/3 下午4:17
 */
enum class OrderStatusTypeEnum(val value: Int) {
//    15：待上车，20：正在接人，25：等待乘客上车，30：未上车，35：已上车，40：正在送人，45：已送达
    ORDER_STATUS_NO_START(15),
    ORDER_STATUS_PICKUP(20),
    ORDER_STATUS_WAITING(25),
    ORDER_STATUS_SKIP(30),
    ORDER_STATUS_HAS_CAR(35),
    ORDER_STATUS_SENDING(40),
    ORDER_STATUS_COMPLETE(45),
}

class PassengerModel(

    /**
     * 订单id
     */
    val orderId : Long = 0,
    /**
     * 订单号
     */
    val orderNo : String?,
    /**
     * 订单状态 15：待上车，20：正在接人，25：等待乘客上车，30：未上车，35：已上车，40：正在送人，45：已送达
     */
    val status : Int = 0,
    /**
     * 乘客id
     */
    val customerId : Long = 0,
    /**
     * 乘客姓名
     */
    val customerName : String?,
    /**
     * 乘客电话
     */
    val customerPhone : String?,
    /**
     * 乘客头像
     */
    val customerAvatar : String?,
    /**
     * 订单线路id
     */
    val orderLineId : Long = 0,
    /**
     * 订单乘车人数
     */
    val passengerNum : Int = 0,
    /**
     * 订单关联班次id
     */
    val orderDriverId : Long = 0,
    /**
     * 1待上车 2未上车 3已上车
     */
    var loadType : Int = 0,
    /**
     *  排班顺序、接人顺序
     */
    val shiftNo : Int = 0,
    /**
     * 排序
     */
    val sort : Int = 0,
    /**
     * 订单乘客上下车点集合
     */
    val orderAddress : MutableList<StationModel>?

) : IModel, Serializable