package com.rvakva.bus.personal.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 下午2:27
 */
class HistoryScheduleProvider : BaseItemProvider<ScheduleDataModel>() {


    override fun convert(helper: BaseViewHolder, item: ScheduleDataModel) {

    }

    override val itemViewType: Int
        get() = OrderStatusTypeEnum.ORDER_TYPE_COMPLETE.value
    override val layoutId: Int
        get() = R.layout.item_history_schedule
}