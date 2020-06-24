package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.personal.PersonalService
import com.rvakva.bus.personal.model.FinanceModel
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         孙艺
 * @CreateDate:     2020/6/24 11:20 AM
 */
class IncomeDetailsViewModel(application: Application) : AndroidViewModel(application)  {

    val incomeLiveData by RequestLiveData<EmResult<FinanceModel>>()

    /**
     * 获取流水详情
     */
    fun  getFlowingDetails(orderId: Int){
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getFlowingDetails(orderId)
                .requestMap()
        },requestLiveData = incomeLiveData,showToastBar = true)
    }

    /**
     * 获取收入详情nt
     */
    fun  getIncomeDetails(orderId: Int){
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getIncomeDetails(orderId)
                .requestMap()
        },requestLiveData = incomeLiveData,showToastBar = true)
    }
}