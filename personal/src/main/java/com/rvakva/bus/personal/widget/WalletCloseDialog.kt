package com.rvakva.bus.personal.widget

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rvakva.bus.personal.R
import com.rvakva.travel.devkit.expend.bindPrizeFilters
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.widget.ToastBar
import com.sherloki.commonwidget.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_wallet_close.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:结算弹窗
 * @Author: lch
 * @Date: 2020/6/24 15:54
 **/
class WalletCloseDialog private constructor() : BaseDialogFragment() {
    companion object {

        private const val CLICK_BLOCK = "CLICK_BLOCK"

        fun newInstance(block: ((prise: String) -> Unit)? = null) =
            WalletCloseDialog().apply {
                arguments = bundleOf(
                    CLICK_BLOCK to block
                )
            }
    }

    override fun getViewWidth(): Int  =
        Resources.getSystem().displayMetrics.widthPixels - 32.dpToPx<Int>()

    override fun getWindowAnimation(): Int = 0

    override fun initView(view: View?) {
        arguments?.let { bundle ->

            wallerDialogEtValue.bindPrizeFilters(3)

            //禁止长按阻止复制
            wallerDialogEtValue.isLongClickable = false

            wallerDialogImgX.setOnClickListener { dismiss() }

            wallerDialogTvGo.setOnClickListener { _ ->
                wallerDialogEtValue.text.let {
                    if (it.isNotEmpty()) {
                        dismiss()
                        (bundle.get(CLICK_BLOCK) as? (String) -> Unit)?.invoke(
                            it.toString()
                        )
                    } else {
                        ToastBar.show("请输入结算金额")
                    }
                }
            }
        }
    }

    override fun getViewHeight(): Int = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getLayoutId(): Int = R.layout.dialog_wallet_close
}