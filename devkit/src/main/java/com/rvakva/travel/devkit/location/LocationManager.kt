package com.rvakva.travel.devkit.location

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.expend.NotificationEnum
import com.rvakva.travel.devkit.expend.buildNotification
import com.rvakva.travel.devkit.expend.createLocation
import com.rvakva.travel.devkit.expend.loge

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午10:28
 */
class LocationManager {

    private object SingletonHolder {
        val INSTANCE = LocationManager()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
        private const val INTERVAL = 3 * 1_000L
    }

    private val client = AMapLocationClient(Ktx.getInstance().app)

    init {
        client.let { it ->
            it.setLocationListener {
                if (it.errorCode == 0) {
                    it.createLocation().apply {
                        KtxViewModel.locationLiveData.postValue(this)
                    }
                } else {
                    "定位失败，错误码：${it.errorCode}".loge()
                }
            }
            it.setLocationOption(createOption())
            NotificationEnum.LOCATION.let { notificationEnum ->
                notificationEnum.buildNotification()?.let { notification ->
                    it.enableBackgroundLocation(notificationEnum.channelCode, notification)
                }
            }
        }
    }

    private fun createOption() =
        AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            isNeedAddress = true
            interval = INTERVAL
            isLocationCacheEnable = false
        }

    fun startLocation() {
        client.startLocation()
    }

    fun destroyClient() {
        client.stopLocation()
    }

}