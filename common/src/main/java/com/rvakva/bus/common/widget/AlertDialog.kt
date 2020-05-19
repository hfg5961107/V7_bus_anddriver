package com.rvakva.bus.common.widget

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.rvakva.bus.common.R
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.sherloki.commonwidget.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_alert.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:22
 */
enum class AlertExceptionEnum(val code: Int) {
    IS_TAKEN(ApiConstant.ERROR_CODE_ORDER_IS_TAKEN),
    ORDER_CANCELED(ApiConstant.ERROR_CODE_ORDER_CANCELED),
    DRIVER_FROZEN(ApiConstant.ERROR_CODE_DRIVER_FROZEN),
    NOT_ENOUGH(ApiConstant.ERROR_CODE_NOT_ENOUGH),
    OUT_OF_LIMIT(ApiConstant.ERROR_CODE_OUT_OF_LIMIT),

}

interface OnAlertDialogClickListener {
    fun onAlertDialogLeftClick(code: Int)
    fun onAlertDialogRightClick(code: Int)
}

interface OnAlertDialogDismissListener {
    fun onAlertDialogDismiss()
}

class AlertDialog private constructor() : BaseDialogFragment() {

    companion object {
        private const val CONTENT = "CONTENT"
        private const val LEFT_CONTENT = "LEFT_CONTENT"
        private const val RIGHT_CONTENT = "RIGHT_CONTENT"
        private const val EXCEPTION_ENUM = "EXCEPTION_ENUM"

        fun newInstance(code: Int) =
            when (AlertExceptionEnum.values()
                .firstOrNull {
                    it.code == code
                }) {
                AlertExceptionEnum.IS_TAKEN -> newInstance(
                    "该订单已被抢",
                    exceptionEnum = AlertExceptionEnum.IS_TAKEN
                )
                AlertExceptionEnum.NOT_ENOUGH -> newInstance(
                    "",
                    "去充值",
                    "取消",
                    exceptionEnum = AlertExceptionEnum.NOT_ENOUGH
                )
                AlertExceptionEnum.DRIVER_FROZEN -> newInstance(
                    "你的账号已被禁用，请联系管理员",
                    exceptionEnum = AlertExceptionEnum.DRIVER_FROZEN
                )
                AlertExceptionEnum.ORDER_CANCELED -> newInstance(
                    "用户已取消该订单",
                    exceptionEnum = AlertExceptionEnum.ORDER_CANCELED
                )
                AlertExceptionEnum.OUT_OF_LIMIT -> newInstance(
                    "接单数量超出限制",
                    exceptionEnum = AlertExceptionEnum.OUT_OF_LIMIT
                )
                else -> null
            }

        fun newInstance(
            content: String,
            leftContent: String = "确定",
            rightContent: String? = null,
            exceptionEnum: AlertExceptionEnum? = null
        ) =
            AlertDialog().apply {
                arguments = bundleOf(
                    EXCEPTION_ENUM to exceptionEnum,
                    CONTENT to content,
                    LEFT_CONTENT to leftContent,
                    RIGHT_CONTENT to rightContent
                )
            }
    }

    override fun onAttach(context: Context) {
        onAlertDialogClickListener = context as? OnAlertDialogClickListener
        onAlertDialogDismissListener = context as? OnAlertDialogDismissListener
        super.onAttach(context)
    }

    private var onAlertDialogClickListener: OnAlertDialogClickListener? = null
    private var onAlertDialogDismissListener: OnAlertDialogDismissListener? = null


    override fun getLayoutId() = R.layout.dialog_alert

    override fun initView(view: View?) {
        arguments?.let { bundle ->
            alertDialogTvContent.text = bundle.getString(CONTENT)

            alertDialogTvLeftAction.run {
                text = bundle.getString(LEFT_CONTENT)
                setOnClickListener {
                    dismiss()
                    onAlertDialogClickListener?.onAlertDialogLeftClick(
                        (bundle.getSerializable(
                            EXCEPTION_ENUM
                        ) as? AlertExceptionEnum)?.code ?: -1
                    )
                }

                alertDialogTvRightAction.run {
                    bundle.getString(RIGHT_CONTENT)?.let {
                        text = it
                        visibility = View.VISIBLE
                        setOnClickListener {
                            dismiss()
                            onAlertDialogClickListener?.onAlertDialogRightClick(
                                (bundle.getSerializable(
                                    EXCEPTION_ENUM
                                ) as? AlertExceptionEnum)?.code ?: -1
                            )
                        }
                    }
                }

                if (bundle.getSerializable(EXCEPTION_ENUM) == AlertExceptionEnum.NOT_ENOUGH) {
                    Ktx.getInstance().userDataSource.userConfigLiveData.observe(
                        viewLifecycleOwner,
                        Observer {
                            alertDialogTvContent.text = "至少有${it.balanceLimit}元余额才能接单"
                        })
                }
            }
        }
    }

    override fun getWindowAnimation() = 0

    override fun getViewHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getViewWidth() =
        Resources.getSystem().displayMetrics.widthPixels - 32.dpToPx<Int>()

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onAlertDialogDismissListener?.onAlertDialogDismiss()
    }

}