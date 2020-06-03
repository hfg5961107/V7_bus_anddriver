package com.rvakva.bus.personal.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.model.BillModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午3:34
 */
class BillListProvider : BaseItemProvider<BillModel>() {


    override fun convert(helper: BaseViewHolder, item: BillModel) {

    }

    override val itemViewType: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.item_bill_list
}