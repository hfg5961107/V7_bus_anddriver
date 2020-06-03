package com.rvakva.bus.home.ui.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/3 下午3:43
 */
class RunOrderProvider : OrderProvider() {

    override val itemViewType: Int
        get() = ScheduleStatusTypeEnum.SCHEDULE_TYPE_ING.value

    override val layoutId: Int
        get() = R.layout.fragment_home_order_item_new

    override fun convert(helper: BaseViewHolder, item: ScheduleDataModel) {
        super.convert(helper, item)

    }
}