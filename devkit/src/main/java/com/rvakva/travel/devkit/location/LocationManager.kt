package com.rvakva.travel.devkit.location

import androidx.lifecycle.Observer
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.google.gson.Gson
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.model.DriverStatusPojo
import com.rvakva.travel.devkit.model.LocationModel
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.mqtt.MqttManager
import com.rvakva.travel.devkit.x.XDataBase
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.MqttPersistenceException
import org.json.JSONObject


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
        private const val INTERVAL = 5 * 1_000L
    }

    private val client = AMapLocationClient(Ktx.getInstance().app)

    init {
        client.let { it ->
            it.setLocationListener {
                if (it.errorCode == 0) {
                    it.createLocation().apply {
                        KtxViewModel.locationLiveData.postValue(this)
                        pushDataByMqtt(this)
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

    var user : UserInfoModel? = null

    fun pushDataByMqtt(loc: LocationModel) {
        XDataBase.getInstance().userInfoModelDao().getUserInfo().observeForever(Observer {
            user = it
        })
        user?.let {
            MqttManager.getInstance().publish(loc.createLocationMessage(it))
        }
    }



}