package com.rvakva.bus.home.ui.adapter

import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.sherloki.simpleadapter.adapter.SimpleMultipleProAdapter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:21
 */
class OrderAdapter(var orderStatusType: ScheduleStatusTypeEnum?) :
    SimpleMultipleProAdapter<ScheduleDataModel>() {

    override fun addItemProvider() {
        addItemProvider(NewOrderProvider())
        addItemProvider(RunOrderProvider())
    }

    override fun getItemType(data: List<ScheduleDataModel>, position: Int) =
        orderStatusType?.value ?: ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW.value

}