package com.rvakva.travel.devkit.expend

import com.amap.api.location.AMapLocation
import com.rvakva.travel.devkit.model.LocationModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午10:42
 */
fun AMapLocation.createLocation() =
    LocationModel(
        accuracy = this.accuracy,
        adCode = this.adCode,
        cityCode = this.city,
        altitude = this.altitude,
        bearing = this.bearing,
        longitude = this.longitude,
        latitude = this.latitude,
        locationType = this.locationType,
        provider = this.provider,
        speed = this.speed,
        time = this.time,
        address = this.address
    )