package com.rvakva.travel.devkit.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.rvakva.travel.devkit.R
import kotlinx.android.synthetic.main.title_bar.view.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:23
 */
class MyTitleBar : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this, true)
    }

    val leftTv = titleBarTvLeft

    val centerText = titleBarTv

    val rightTv = titleBarTvRight

    val container = titleBarRl

    fun setCustomContentView(layoutId: Int) {
        titleBarTv.visibility = View.GONE
        titleBarFl.visibility = View.VISIBLE
        LayoutInflater.from(context).inflate(layoutId, titleBarFl, true)
    }
}