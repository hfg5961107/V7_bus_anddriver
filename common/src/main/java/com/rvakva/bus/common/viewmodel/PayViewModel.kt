package com.rvakva.bus.common.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.CommonService
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:38
 */
class PayViewModel(application: Application) : AndroidViewModel(application) {
    val chargeLiveData by RequestLiveData<EmResult<Any>>()

    fun charge(fee: String,payType:String) {
        launchRequest(block = {
            ApiManager.getInstance().createService(CommonService::class.java)
                .recharge(payType, fee)
                .requestMap()

        }, requestLiveData = chargeLiveData)
    }
}