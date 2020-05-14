package com.rvakva.travel.devkit.expend

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:14
 */
fun jumpByARouter(path: String, block: Postcard.() -> Postcard = { this }) {
    ARouter.getInstance().build(path)
        .block()
        .navigation()
}

fun Context.jumpToHome() = Intent(Intent.ACTION_MAIN).let {
    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    it.addCategory(Intent.CATEGORY_HOME)
    startActivity(it)
}

inline fun <reified T : Activity> Context.jumpTo(block: Intent.() -> Intent = { this }) {
    startActivity(Intent(this, T::class.java).block())
}