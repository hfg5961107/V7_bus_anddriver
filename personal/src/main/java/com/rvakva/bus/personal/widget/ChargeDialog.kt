package com.rvakva.bus.personal.widget

import android.content.res.Resources
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rvakva.bus.personal.R
import com.rvakva.travel.devkit.expend.bindPrizeFilters
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.widget.ToastBar
import com.sherloki.commonwidget.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_charge.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午2:02
 */
enum class WalletType {
    CHARGE, CASH_OUT
}

class ChargeDialog private constructor() : BaseDialogFragment() {

    companion object {

        private const val WALLET_TYPE = "WALLET_TYPE"
        private const val CLICK_BLOCK = "CLICK_BLOCK"

        fun newInstance(walletType: WalletType, block: ((prise: String) -> Unit)? = null) =
            ChargeDialog().apply {
                arguments = bundleOf(
                    WALLET_TYPE to walletType,
                    CLICK_BLOCK to block
                )
            }

    }

    override fun getViewHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getViewWidth() = Resources.getSystem().displayMetrics.widthPixels - 32.dpToPx<Int>()

    override fun getWindowAnimation() = 0

    override fun getLayoutId() = R.layout.dialog_charge

    override fun initView(view: View?) {
        arguments?.let { bundle ->
            if (bundle.getSerializable(WALLET_TYPE) == WalletType.CHARGE) {
                chargeDialogTvTitle.text = "充值金额"
                chargeDialogTvDesc.text = "仅支持支付宝充值"
                chargeDialogTvAction.text = "确认充值"
                chargeDialogEtValue.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                chargeDialogEtValue.bindPrizeFilters()
            } else {
                chargeDialogTvTitle.text = "提现金额"
                chargeDialogTvDesc.text = "发起提现申请"
                chargeDialogTvAction.text = "确认提现"
                chargeDialogEtValue.inputType = InputType.TYPE_CLASS_NUMBER
                chargeDialogEtValue.bindPrizeFilters(3)
            }
            chargeDialogTvCancel.setOnClickListener { dismiss() }
            chargeDialogTvAction.setOnClickListener { _ ->
                chargeDialogEtValue.text.let {
                    if (it.isNotEmpty()) {
                        dismiss()
                        (bundle.get(CLICK_BLOCK) as? (String) -> Unit)?.invoke(
                            it.toString()
                        )
                    } else {
                        ToastBar.show("请输入金额")
                    }
                }
            }
        }
    }
}