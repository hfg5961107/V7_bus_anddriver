package com.rvakva.travel.devkit.x

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/4/28 下午2:59
 */
class XActivityLifeCycleCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        activity?.let {
            Ktx.getInstance().activityManager.remove(it)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity?.let {
            Ktx.getInstance().activityManager.push(it)
//            if (it is ISocketReceiver) {
//                it.registerReceiver()
//            }
        }
    }

    override fun onActivityResumed(activity: Activity) {

    }
}