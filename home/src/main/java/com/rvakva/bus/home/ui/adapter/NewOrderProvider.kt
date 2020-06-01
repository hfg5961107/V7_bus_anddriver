package com.rvakva.bus.home.ui.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/21 下午4:17
 */
class NewOrderProvider : OrderProvider() {

    override val itemViewType: Int
        get() = OrderStatusTypeEnum.ORDER_TYPE_NEW.value

    override val layoutId: Int
        get() = R.layout.fragment_home_order_item_new

    override fun convert(helper: BaseViewHolder, item: ScheduleDataModel) {
        super.convert(helper, item)
        resetView(
            helper
//            R.id.fragmentMainOrderItemContentTvTakeDistance,
//            R.id.fragmentMainOrderItemContentTvSendDistance,
//            R.id.fragmentMainOrderItemContentVTakeDistance,
//            R.id.fragmentMainOrderItemContentVSendDistance
        )
    }
}