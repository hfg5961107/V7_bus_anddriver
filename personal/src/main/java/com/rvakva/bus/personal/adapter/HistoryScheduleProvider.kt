package com.rvakva.bus.personal.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.formatDate

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 下午2:27
 */
class HistoryScheduleProvider : BaseItemProvider<ScheduleDataModel>() {

    private fun createType(data: ScheduleDataModel) = run {
        when (data.lineType) {
            1 -> "站点 - 站点"
            2 -> "站点 - 送人"
            3 -> "接人 - 送人"
            4 -> "接人 - 站点"
            else -> "站点 - 站点"
        }
    }

    override fun convert(helper: BaseViewHolder, item: ScheduleDataModel) {
        helper.setText(R.id.pcHistoryCarNoTv, "${item.licenseNo!!.substring(0,2)} ${item.licenseNo!!.substring(2,item.licenseNo!!.length)} / ${item.vehicleSeat}座")

        helper.setText(R.id.pcHistoryTypeTv, createType(item))

        helper.setText(R.id.pcHistoryAddressTv, "${item.startStationName} - ${item.endStationName}")

//        helper.setText(R.id.pcHistoryBeginTimeTv,"${formatDate(item.schedulingTime * 1000, Config.PATTERN_YYYY_MM_DD)} · 检票${item.startLineTime} · 出发${item.endLineTime}")

        helper.setText(R.id.pcHistoryTimeOneTv,formatDate(item.schedulingTime * 1000, Config.PATTERN_YYYY_MM_DD))
        helper.setText(R.id.pcHistoryTimeTwoTv,"检票${item.startLineTime}")
        helper.setText(R.id.pcHistoryTimeThreeTv,"出发${item.endLineTime}")

        if (item.status == Config.SCHEDULE_TYPE_COMPLETE){
            helper.setText(R.id.pcHistoryTimeTv,"完成时间： ${formatDate(item.finishTime * 1000, Config.PATTERN_YYYY_MM_DD_HH_MM)}")
            helper.setText(R.id.pcHistoryStatus,"已完成")
        }else if (item.status == Config.SCHEDULE_TYPE_CANCEL){
            helper.setText(R.id.pcHistoryTimeTv,"取消时间： ${formatDate(item.finishTime * 1000, Config.PATTERN_YYYY_MM_DD_HH_MM)}")
            helper.setText(R.id.pcHistoryStatus,"已取消")
        }
    }

    override val itemViewType: Int
        get() = ScheduleStatusTypeEnum.SCHEDULE_TYPE_COMPLETE.value
    override val layoutId: Int
        get() = R.layout.item_history_schedule

}