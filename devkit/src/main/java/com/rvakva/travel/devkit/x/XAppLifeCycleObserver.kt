package com.rvakva.travel.devkit.x

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.R
import com.rvakva.travel.devkit.expend.getString
import com.rvakva.travel.devkit.widget.ToastBar

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/28 上午9:48
 */
class XAppLifeCycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopEvent() {
        //APP存在于后台
        ToastBar.show("已离开${R.string.app_name.getString()},请注意信息安全")
        Config.IS_FOREGROUND = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartEvent() {
        //APP存在于前台
        Config.IS_FOREGROUND = true
    }

}