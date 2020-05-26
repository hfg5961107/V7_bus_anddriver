package com.rvakva.bus.home.ui.adapter

import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.ui.work.OrderStatusTypeEnum
import com.sherloki.simpleadapter.adapter.SimpleMultipleProAdapter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:21
 */
class OrderAdapter(var orderStatusType: OrderStatusTypeEnum?) :
    SimpleMultipleProAdapter<ScheduleDataModel>() {

    override fun addItemProvider() {
        addItemProvider(NewOrderProvider())
        addItemProvider(NewOrderProvider())
        addItemProvider(NewOrderProvider())
    }

    override fun getItemType(data: List<ScheduleDataModel>, position: Int) =
        orderStatusType?.value ?: OrderStatusTypeEnum.ORDER_TYPE_NEW.value

}