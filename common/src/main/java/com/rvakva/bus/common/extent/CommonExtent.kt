package com.rvakva.bus.common.extent

import android.app.Activity
import com.rvakva.bus.common.WebActivity
import com.rvakva.travel.devkit.expend.jumpTo

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:13
 */
fun Activity.jumpToWebActivity(url: String, title: String) = jumpTo<WebActivity> {
    putExtra(WebActivity.URL, url)
    putExtra(WebActivity.TITLE, title)
}