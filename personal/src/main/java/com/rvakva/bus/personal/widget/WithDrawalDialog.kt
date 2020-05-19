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
import kotlinx.android.synthetic.main.dialog_withdrawal.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:    提现弹窗
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午2:02
 */


class WithDrawalDialog private constructor() : BaseDialogFragment() {

    companion object {

        const val CLICK_BLOCK = "CLICK_BLOCK"

        fun newInstance(block: ((prise: String) -> Unit)? = null) =
            WithDrawalDialog().apply {
                arguments = bundleOf(
                    CLICK_BLOCK to block
                )
            }
    }

    override fun getViewHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getViewWidth() =
        Resources.getSystem().displayMetrics.widthPixels - 32.dpToPx<Int>()

    override fun getWindowAnimation() = 0

    override fun getLayoutId() = R.layout.dialog_withdrawal

    override fun initView(view: View?) {
        arguments?.let { bundle ->

            withdrawalDialogTvTitle.text = "提现金额"
            withdrawalDialogTvDesc.text = "发起提现申请"
            withdrawalDialogTvAction.text = "确认提现"
            withdrawalDialogEtValue.inputType = InputType.TYPE_CLASS_NUMBER
            withdrawalDialogEtValue.bindPrizeFilters(3)

            withdrawalDialogTvCancel.setOnClickListener { dismiss() }
            withdrawalDialogTvAction.setOnClickListener { _ ->
                withdrawalDialogEtValue.text.let {
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