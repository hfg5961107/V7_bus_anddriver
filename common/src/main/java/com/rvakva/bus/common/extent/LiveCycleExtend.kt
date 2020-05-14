package com.rvakva.bus.common.extent

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.rvakva.bus.common.lifecycle.MapLifeCycleObserver
import com.rvakva.bus.common.lifecycle.WebLifeCycleObserver

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:24
 */
fun AppCompatActivity.bindMapLifeCycle(savedInstanceState: Bundle?, mapView: MapView) {
    lifecycle.addObserver(MapLifeCycleObserver(savedInstanceState, mapView))
}

fun AppCompatActivity.bindWebLifeCycle(webView: WebView) {
    lifecycle.addObserver(WebLifeCycleObserver(webView))
}