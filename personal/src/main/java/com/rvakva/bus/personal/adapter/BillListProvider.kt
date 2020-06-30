package com.rvakva.bus.personal.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.model.BillModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.formatDate

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午3:34
 */
class BillListProvider : BaseItemProvider<BillModel>() {

    override fun convert(helper: BaseViewHolder, item: BillModel) {
        when(item.type){
            2->{
                helper.setText(R.id.billTypeTv,"收入")
                helper.setText(R.id.billMoneyTv,"+ ${item.fee}")
                helper.setText(R.id.billCarNoSeatTv,"${item.licenseNo} / ${item.vehicleSeat}座")
                helper.setTextColor(R.id.billCarNoSeatTv,R.color.black_sub)
            }
            3->{
                helper.setText(R.id.billTypeTv,"充值")
                helper.setText(R.id.billMoneyTv,"+ ${item.fee}")
                helper.setText(R.id.billCarNoSeatTv,if (item.rechargeType == Config.CHANNEL_APP_ALI){"支付宝"}else{"微信"})
                helper.setTextColor(R.id.billCarNoSeatTv,R.color.black_sub)
            }
            4->{
                helper.setText(R.id.billTypeTv,"结算")
                helper.setText(R.id.billMoneyTv,"- ${item.fee}")
                //1-审核中 2-审核通过 3-审核拒绝
                if (item.status==1){
                    helper.setText(R.id.billCarNoSeatTv,"待审核")
                    helper.setTextColor(R.id.billCarNoSeatTv,R.color.color_yellow)
                }else if (item.status==2){
                    helper.setText(R.id.billCarNoSeatTv,"已通过")
                        helper.setTextColor(R.id.billCarNoSeatTv,R.color.black_sub)
                }else if (item.status==3){
                    helper.setText(R.id.billCarNoSeatTv,"已拒绝")
                    helper.setTextColor(R.id.billCarNoSeatTv,R.color.black_sub)
                }
            }
        }

        helper.setText(R.id.billTimeTv,"${formatDate(item.created * 1000, Config.PATTERN_YYYY_MM_DD_HH_MM)}")
    }

    override val itemViewType: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.item_bill_list
}