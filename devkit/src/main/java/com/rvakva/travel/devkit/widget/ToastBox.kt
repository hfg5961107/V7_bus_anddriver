package com.rvakva.travel.devkit.widget

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rvakva.travel.devkit.R
import com.sherloki.commonwidget.BaseDialogFragment
import kotlinx.android.synthetic.main.toast_box.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:21
 */
class ToastBox private constructor() : BaseDialogFragment() {

    companion object {
        private const val TOAST_DESC = "TOAST_DESC"

        fun newInstance(desc: String = "加载中...") =
            ToastBox().apply {
                arguments = bundleOf(
                    TOAST_DESC to desc
                )
            }
    }

    override fun getViewHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getViewWidth() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getWindowAnimation() = 0

    override fun getLayoutId() = R.layout.toast_box

    override fun initView(view: View?) {
        isCancelable = false

        toastBoxIv.let {
            (it.background as? AnimationDrawable)?.run {
                start()
            }
        }
        arguments?.getString(TOAST_DESC)?.let {
            toastBoxTv.text = it
        }
    }
}