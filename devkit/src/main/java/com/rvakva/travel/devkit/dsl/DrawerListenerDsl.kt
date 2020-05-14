package com.rvakva.travel.devkit.dsl

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:09
 */
class DrawerListenerDsl {

    private var onDrawerStateChanged: ((Int) -> Unit)? = null
    private var onDrawerSlide: ((View, Float) -> Unit)? = null
    private var onDrawerClosed: ((View) -> Unit)? = null
    private var onDrawerOpened: ((View) -> Unit)? = null

    fun onDrawerStateChanged(method: ((newState: Int) -> Unit)) {
        onDrawerStateChanged = method
    }

    fun onDrawerSlide(method: ((drawerView: View, slideOffset: Float) -> Unit)) {
        onDrawerSlide = method
    }

    fun onDrawerClosed(method: ((drawerView: View) -> Unit)) {
        onDrawerClosed = method
    }

    fun onDrawerOpened(method: ((drawerView: View) -> Unit)) {
        onDrawerOpened = method
    }

    val drawerListener = object : DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {
            onDrawerStateChanged?.invoke(newState)
        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            onDrawerSlide?.invoke(drawerView, slideOffset)
        }

        override fun onDrawerClosed(drawerView: View) {
            onDrawerClosed?.invoke(drawerView)
        }

        override fun onDrawerOpened(drawerView: View) {
            onDrawerOpened?.invoke(drawerView)
        }
    }

    fun addDrawerListener(drawerLayout: DrawerLayout) {
        drawerLayout.addDrawerListener(drawerListener)
    }
}