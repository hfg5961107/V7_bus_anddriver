package com.rvakva.bus.personal.model

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author: lch
 * @Date: 2020/6/28 09:34
 **/
class BusinessListModel {

    /**
     * amountMoney : 0
     * data : [{"created":0,"driverIncome":0,"endLineTime":"string","endStationName":"string","id":0,"licenseNo":"string","orderId":0,"orderNo":"string","passengerNum":0,"schedulingTime":0,"serialNo":"string","startLineTime":"string","startStationName":"string","vehicleSeat":0}]
     * total : 0
     */
     var amountMoney : Double? = null
     var total : Int = 0
     var flowing: List<DataBean?>? = null

    class DataBean {
        /**
         * created : 0
         * driverIncome : 0
         * endLineTime : string
         * endStationName : string
         * id : 0
         * licenseNo : string
         * orderId : 0
         * orderNo : string
         * passengerNum : 0
         * schedulingTime : 0
         * serialNo : string
         * startLineTime : string
         * startStationName : string
         * vehicleSeat : 0
         */
        var created : Long= 0
        var driverIncome : Double = 0.0
        var endLineTime: String = ""
        var endStationName: String = ""
        var id :Int= 0
        var licenseNo: String = ""
        var orderId : Int = 0
        var orderNo: String = ""
        var passengerNum: Int = 0
        var schedulingTime : Long= 0
        var serialNo: String = ""
        var startLineTime: String = ""
        var startStationName: String = ""
        var vehicleSeat : Int = 0

    }

}