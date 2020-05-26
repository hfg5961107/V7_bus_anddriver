package com.rvakva.travel.devkit.model

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午10:42
 */
class LocationModel(
    val accuracy: Float = 0F,
    val adCode: String? = null,
    val cityCode: String? = null,
    val altitude: Double = 0.0,
    val bearing: Float = 0F,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val locationType: Int = 0,
    val provider: String? = null,
    val speed: Float = 0F,
    val time: Long = 0L,
    val address: String? = null
) : IModel