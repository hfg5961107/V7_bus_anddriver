package com.rvakva.bus.personal.adapter

import com.rvakva.bus.common.model.ScheduleDataModel
import com.sherloki.simpleadapter.adapter.SimpleMultipleProAdapter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 下午2:14
 */
class HistoryScheduleAdapter
    : SimpleMultipleProAdapter<ScheduleDataModel>() {

    override fun addItemProvider() {
        addItemProvider(HistoryScheduleProvider())
    }

    override fun getItemType(data: List<ScheduleDataModel>, position: Int) =
        data[position].status


}