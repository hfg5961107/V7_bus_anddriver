package com.rvakva.bus.personal.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import androidx.activity.viewModels
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.model.FinanceModel
import com.rvakva.bus.personal.viewmodel.IncomeDetailsViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.formatDate
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.retrofit.exception.SpecialApiException
import kotlinx.android.synthetic.main.activity_income_details.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         孙艺
 * @CreateDate:     2020/6/24 上午10:38
 */
@Route(path = Config.USER_INCOME_DETAILS)
class IncomeDetailsActivity : KtxActivity(R.layout.activity_income_details) {
    // 是否是收入类型  默认收入详情 外部传入
    private var isIncomeType: Boolean = true
    // 订单id 外部传入
    private var orderId: Int = 0

    private val incomeActivityViewModel by viewModels<IncomeDetailsViewModel>()

    override fun initTitle() {
        orderId = intent.getIntExtra("orderId",0)
        isIncomeType = intent.getBooleanExtra("isIncomeType", true)

        hisScheduleMtb?.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            if (isIncomeType) {
                it.centerText.text = "收入详情"
            } else {
                it.centerText.text = "流水详情"
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {

        if (!isIncomeType) {
            realFeeLayout.visibility = View.GONE
            commissionRatioLayout.visibility = View.GONE
        }
    }

    override fun initObserver() {
        incomeActivityViewModel.incomeLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it?.let {
                        showData(it.data)
                    }
                },
                failBlock = {
                    (it as? SpecialApiException)?.let {
                    } ?: finish()
                }
            )
        )
        var textSpan: SpannableString = SpannableString("90元");
        textSpan.setSpan( AbsoluteSizeSpan(24), 0, textSpan.length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        textSpan.setSpan( AbsoluteSizeSpan(16), textSpan.length - 1, textSpan.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        incomeLabel.text = textSpan;

    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        }
    }

    private fun requestData() {
        if (isIncomeType) {
            incomeActivityViewModel.getIncomeDetails(orderId)
        } else {
            incomeActivityViewModel.getFlowingDetails(orderId)
        }
    }


    private fun showData(model: FinanceModel?) {
        if (model != null) {
            orderNumLabel.text = model.orderNo

            completeLabel.text = formatDate(model.created * 1000, Config.PATTERN_YYYY_MM_DD_HH_MM)
            carInfoLabel.text = model.licenseNo + " / " + model.vehicleSeat
            buyTicketLabel.text = model.passengerNum.toString() + "人"

            passengersLabel.text = model.passengerName
            startAddressLabel.text = model.startStationName
            endAddressLabel.text = model.endStationName

            priceLabel.text = model.orderFee.toString()
            discountLabel.text = model.couponFee.toString()

            var realFee = ""
            if (isIncomeType) {
                realPayLabel.text = model.realFee.toString()
                commissionRatioLabel.text = model.proportion.toString()
                realFee = model.driverIncome.toString() + "元"
            } else {
                realFee = model.realFee.toString() + "元"
            }
            var textSpan: SpannableString = SpannableString(realFee);
            textSpan.setSpan(
                AbsoluteSizeSpan(16),
                textSpan.length - 2,
                textSpan.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            );
            incomeLabel.text = textSpan;
        }
    }
}