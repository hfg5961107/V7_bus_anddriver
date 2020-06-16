package com.rvakva.bus.home.ui.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.formatDate

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/21 下午4:18
 */
abstract class OrderProvider : BaseItemProvider<ScheduleDataModel>() {

    private fun createType(data: ScheduleDataModel) = run {
        when (data.lineType) {
            1 -> "站点 - 站点"
            2 -> "站点 - 送人"
            3 -> "接人 - 送人"
            4 -> "接人 - 站点"
            else -> "站点 - 站点"
        }
    }


//    fun resetView(helper: BaseViewHolder, vararg ids: Int) {
//        for (id in ids) {
//            helper.getView<View>(id).let {
//                if (it is TextView) {
//                    it.let {
//                        it.setImageResource()
//                        (it.layoutParams as? RelativeLayout.LayoutParams)?.let { layoutParams ->
//                            it.layoutParams = layoutParams.apply {
//                                removeRule(RelativeLayout.CENTER_IN_PARENT)
//                            }
//                        }
//                    }
//                } else {
//                    it.visibility = View.GONE
//                }
//            }
//        }
//    }

    override fun convert(helper: BaseViewHolder, item: ScheduleDataModel) {
        helper.setText(R.id.homeOrderCarNoTv, "${item.licenseNo!!.substring(0,2)} ${item.licenseNo!!.substring(2,item.licenseNo!!.length)} / ${item.vehicleSeat}座")

        helper.setText(R.id.homeOrderTypeTv, createType(item))

        helper.setText(R.id.homeOrderAddressTv, "${item.startStationName} - ${item.endStationName}")

        helper.setText(R.id.homeOrderTimeTv,"${formatDate(item.schedulingTime * 1000, Config.PATTERN_YYYY_MM_DD)} · 检票${item.startLineTime} · 出发${item.endLineTime}")

        helper.setText(R.id.homeOrderPassengerTv,"乘客 ${item.passengerAllNum}   余座 ${item.remainingSeat}")

        helper.setText(R.id.homeOrderStatus,createStatus(item))
    }

    private fun createStatus(item: ScheduleDataModel) = run {
        when(item.status){
            Config.SCHEDULE_TYPE_NEW -> "等待行程开始"
            Config.SCHEDULE_TYPE_ING -> "行程中"
            else -> "等待行程开始"
        }
    }
}