package com.rvakva.bus.personal.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.model.BusinessListModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.checkIsInt
import com.rvakva.travel.devkit.expend.formatDate

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author: lch
 * @Date: 2020/6/28 09:51
 **/
class BusinessListProvider : BaseItemProvider<BusinessListModel.DataBean>() {

    override fun convert(helper: BaseViewHolder, item: BusinessListModel.DataBean) {

        helper.setText(R.id.billTypeTv, "收入")
        helper.setText(R.id.billMoneyTv, "+ ${item.driverIncome.checkIsInt()}")

        helper.setText(R.id.billCarNoSeatTv, "${item.licenseNo} / ${item.vehicleSeat}座")

        helper.setText(
            R.id.billTimeTv,
            "${formatDate(item.created * 1000, Config.PATTERN_YYYY_MM_DD_HH_MM)}"
        )
    }

    override val itemViewType: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.item_bill_list
}