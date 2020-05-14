package com.rvakva.travel.devkit

import com.rvakva.travel.devkit.livedata.ResumeStateEventLiveData
import com.rvakva.travel.devkit.model.ProgressModel
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午3:55
 */
object KtxViewModel {

    val payStatusLiveData = ResumeStateEventLiveData<Boolean>()
//    val locationLiveData = CreateStateLiveData<LocationModel>()
//    val mqttLiveData = CreateStateEventLiveData<MqttMessage>()
    val progressLiveData = ResumeStateEventLiveData<ProgressModel>()
//    val emptyClickLiveData = ResumeStateEventLiveData<Int>()

}