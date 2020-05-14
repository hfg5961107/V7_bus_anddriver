package com.rvakva.travel.devkit.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leaf.library.StatusBarUtil

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午5:20
 */
abstract class KtxActivity(layoutId : Int) : AppCompatActivity(layoutId) {

    private var isFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetting()
        initStatusBar()
        initTitle()
        initView(savedInstanceState)
        initObserver()
        isFirstInit = true
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

}