package com.rvakva.bus.home.viewmodel.work

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rvakva.bus.common.CommonService
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.location.LocationManager
import com.rvakva.travel.devkit.mqtt.MqttManager
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午5:33
 */
class WorkViewModel(application: Application) : AndroidViewModel(application){

    val workStatusViewModel by RequestLiveData<BaseResult>()

    private val userRepository = UserRepository()

    private val MQTT_TIMEOUT = 30 * 1_000L

    fun showNotification(isNewOrder: Boolean, intent: Intent) {
        if (!Config.IS_FOREGROUND) {
            (if (isNewOrder)
                NotificationEnum.MSG_NEW_ORDER
            else
                NotificationEnum.MSG_NEW_ASSIGN)
                .let {
                    it.buildNotification(intent)?.showNotification(it.channelCode)
                }
        }
    }

    fun getMqttConfig() {
        launchRepeat(delayTimeMillis = MQTT_TIMEOUT) {
            ApiManager.getInstance().createService(HomeService::class.java)
                .getMqttConfig()
                .requestMap()
                .apply {
                    data?.let { it ->
                        if (it != MqttManager.getInstance().mqttConfig) {
                            MqttManager.getInstance().initConnect(it)
                        } else {
                            MqttManager.getInstance().changeTopic(it)
                        }
                    }
                }

        }
    }

    override fun onCleared() {
        super.onCleared()
        MqttManager.getInstance().destroyConnect()
        LocationManager.getInstance().destroyClient()
    }

    fun startLocation() = LocationManager.getInstance().startLocation()


    fun changeStatus(status: Int) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .changeStatus(status)
                    .requestMap()
            }, requestLiveData = workStatusViewModel,showToastBox = false,showToastBar = false
        )
    }

    fun getUserInfo() {
        launchRequest(block = {
            userRepository.getUserInfo()
        })
    }

    fun getUserStatistics() {
        launch {
            userRepository.getUserStatistics()
        }
    }

    fun getUserConfig() {
        launchRequest(block = {
            userRepository.getUserConfig()
        })
    }
}