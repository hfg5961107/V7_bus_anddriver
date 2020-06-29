package com.rvakva.travel.devkit.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leaf.library.StatusBarUtil
import com.rvakva.travel.devkit.exception.CrashHandler
import com.rvakva.travel.devkit.exception.CrashHandlerCallBack
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.mqtt.MqttManager

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午5:20
 */
abstract class KtxActivity(layoutId : Int) : AppCompatActivity(layoutId) , CrashHandlerCallBack {

    private var isFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetting()
        initStatusBar()
        initTitle()
        initView(savedInstanceState)
        initObserver()
        isFirstInit = true
        CrashHandler.getInstance().setCallBack(this);
    }

    override fun onResume() {
        super.onResume()
        initData(isFirstInit)
        isFirstInit = false
    }

     open fun initSetting(){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    open fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(this, Color.WHITE)
            StatusBarUtil.setDarkMode(this)
        } else {
            StatusBarUtil.setColor(this, Color.BLACK)
        }
    }

    abstract fun initTitle()

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initObserver()

    abstract fun initData(isFirstInit: Boolean)


    override fun uncaughtException(thread: Thread?, throwable: Throwable?) {
        //异常上报服务器
        MqttManager.getInstance().publishStatusMessage(3,CrashHandler.getInstance().saveCatchInfo2File(this, throwable))
    }
}