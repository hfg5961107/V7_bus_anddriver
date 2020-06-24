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
 * @CreateDate:     2020/5/15 下午4:17
 */
class ChargeDialog private constructor() : BaseDialogFragment()  {

    companion object {

        private const val CLICK_BLOCK = "CLICK_BLOCK"

        fun newInstance(block: ((prise: String,payType:String) -> Unit)? = null) =
            ChargeDialog().apply {
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

            chargeDialogEtValue.bindPrizeFilters(3)

            //禁止长按阻止复制
            chargeDialogEtValue.isLongClickable = false

            chargeCancelIv.setOnClickListener { dismiss() }

            chargeAlipayLl.setOnClickListener { _ ->
                chargeDialogEtValue.text.let {
                    if (it.isNotEmpty()) {
                        dismiss()
                        (bundle.get(CLICK_BLOCK) as? (String,String) -> Unit)?.invoke(
                            it.toString(),
                            "CHANNEL_APP_ALI"
                        )
                    } else {
//                        ToastBar.show("请输入金额")
                    }
                }
            }
            chargeWechatLl.setOnClickListener { _ ->
                chargeDialogEtValue.text.let {
                    if (it.isNotEmpty()) {
                        dismiss()
                        (bundle.get(CLICK_BLOCK) as? (String,String) -> Unit)?.invoke(
                            it.toString(),
                            "CHANNEL_APP_WECHAT"
                        )
                    } else {
//                        ToastBar.show("请输入金额")
                    }
                }
            }
        }
    }

    override fun getViewHeight(): Int = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getLayoutId(): Int = R.layout.dialog_charge
}