package com.rvakva.travel.devkit.expend

import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/9 下午2:40
 */
fun initMarker(latLng: LatLng?, res: Int, zIndex: Float = 0F) =
    MarkerOptions().apply {
        position(latLng)
        if (res > 0) {
            icon(
                BitmapDescriptorFactory.fromResource(
                    res
                )
            )
        }
        anchor(0.5F, 0.5F)
        rotateAngle(0F)
        if (zIndex > 0) {
            zIndex(zIndex)
        }
    }