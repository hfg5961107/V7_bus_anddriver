package com.rvakva.travel.devkit.widget

import android.view.LayoutInflater
import android.widget.TextView
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.R
import com.sherloki.commonwidget.BaseToastBar

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:26
 */
class ToastBar private constructor() : BaseToastBar() {

    private object SingletonHolder {
        var INSTANCE = ToastBar()
    }

    companion object {

        private fun getInstance() = SingletonHolder.INSTANCE

        fun show(content: String) = getInstance().show(content)
    }


    private fun show(content: String) {
        LayoutInflater.from(Ktx.getInstance().app).inflate(R.layout.toast_bar, null, false).let {
            it.findViewById<TextView>(R.id.toastBarTv)?.text = content
            show(Ktx.getInstance().app, it)
        }
    }
}