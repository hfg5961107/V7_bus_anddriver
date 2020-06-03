package com.rvakva.bus.home.viewmodel.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午4:49
 */
class OrderOperationViewModel(application: Application) : AndroidViewModel(application) {

     val scheduleDetailLiveData by RequestLiveData<EmResult<ScheduleDataModel>>()

    fun qureyScheduleById(scheduleId:Long){
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .queryScheduleById(scheduleId)
                    .requestMap()
            },requestLiveData = scheduleDetailLiveData,showToastBar = true
        )
    }

}