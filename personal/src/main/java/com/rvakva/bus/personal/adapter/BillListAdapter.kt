package com.rvakva.bus.personal.adapter

import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.model.BillModel
import com.sherloki.simpleadapter.adapter.SimpleMultipleProAdapter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午3:33
 */
class BillListAdapter : SimpleMultipleProAdapter<BillModel>() {

    override fun addItemProvider() {
        addItemProvider(BillListProvider())
    }

    override fun getItemType(data: List<BillModel>, position: Int) =
        data[position].type
}