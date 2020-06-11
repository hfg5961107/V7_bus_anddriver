package com.rvakva.bus.common.base

import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.rvakva.bus.common.extent.bindMapLifeCycle
import com.rvakva.travel.devkit.base.KtxActivity

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/29 下午3:57
 */
abstract class MapActivity(layoutId: Int) : KtxActivity(layoutId) {

    abstract val mapView: MapView

    var amap: AMap? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        bindMapLifeCycle(savedInstanceState, mapView)
        mapView.map.let {
            it.mapType = AMap.MAP_TYPE_NORMAL
            it.uiSettings.let {
                it.isZoomControlsEnabled = false
                it.setLogoBottomMargin(-50)
            }
            amap = it
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}