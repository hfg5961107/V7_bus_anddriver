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
//   10:待开始 15：待上车，20：正在接人，25：等待乘客上车，30：未上车，35：已上车，40：正在送人，45：已送达
    ORDER_STATUS_WAIT_START(10),
    ORDER_STATUS_START(15),
    ORDER_STATUS_PICKUP(20),
    ORDER_STATUS_WAITING(25),
    ORDER_STATUS_SKIP(30),
    ORDER_STATUS_HAS_CAR(35),
    ORDER_STATUS_SENDING(40),
    ORDER_STATUS_COMPLETE(45),
}

class PassengerModel : IModel, Serializable{
    /**
     * 订单id
     */
    var orderId : Long = 0
    /**
     * 订单号
     */
    var orderNo : String = ""
    /**
     * 订单状态 10:待开始 15：待上车，20：正在接人，25：等待乘客上车，30：未上车，35：已上车，40：正在送人，45：已送达
     */
    var status : Int = 0
    /**
     * 乘客id
     */
    var customerId : Long = 0
    /**
     * 乘客姓名
     */
    var customerName : String = ""
    /**
     * 乘客电话
     */
    var customerPhone : String = ""
    /**
     * 乘客头像
     */
    var customerAvatar : String = ""
    /**
     * 订单线路id
     */
    var orderLineId : Long = 0
    /**
     * 订单乘车人数
     */
    var passengerNum : Int = 0
    /**
     * 订单关联班次id
     */
    var orderDriverId : Long = 0
    /**
     * 1待上车 2未上车 3已上车
     */
    var loadType : Int = 0
    /**
     *  排班顺序、接人顺序
     */
    var shiftNo : Int = 0
    /**
     * 排序
     */
    var sort : Int = 0
    /**
     * 订单乘客上下车点集合
     */
    var orderAddress : MutableList<StationModel>? = null

    /**
     * 类型 1 图片 0订单
     */
    var type : Int = 0

}