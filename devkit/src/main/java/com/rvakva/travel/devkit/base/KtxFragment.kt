package com.rvakva.travel.devkit.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.rvakva.travel.devkit.expend.loge

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午5:26
 */
abstract class KtxFragment(layoutId : Int) : Fragment(layoutId) {
    private var isFirstInit = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initObserver()
        isFirstInit = true
        this::class.java.name.loge("baseOnViewCreated")
    }


    abstract fun initObserver()

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    abstract fun initData(isFirstInit: Boolean)

    override fun onResume() {
        super.onResume()
        initData(isFirstInit)
        isFirstInit = false
        this::class.java.name.loge("baseOnResume")
    }

    override fun onPause() {
        super.onPause()
        this::class.java.name.loge("baseOnPause")
    }

    override fun onStop() {
        super.onStop()
        this::class.java.name.loge("baseOnStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this::class.java.name.loge("baseOnDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        this::class.java.name.loge("baseOnDestroy")
    }

}