package com.rvakva.travel.devkit.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sherloki.simpleadapter.widget.IRefreshView

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:29
 */
class MySwipeRefreshLayout : SwipeRefreshLayout, IRefreshView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun bindRefreshLayoutEnable(enable: Boolean) {
        isEnabled = enable
    }

    override fun bindRefreshLayoutRefreshing(refreshing: Boolean) {
        isRefreshing = refreshing
    }

    override fun bindRefreshListener(onRefreshBlock: () -> Unit) {
        setOnRefreshListener {
            onRefreshBlock()
        }
    }

    override fun initRefreshLayout() {
    }
}