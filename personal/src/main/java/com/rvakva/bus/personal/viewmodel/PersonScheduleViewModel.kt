package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.model.AppInfoModel
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.repository.AppUpdateRepository
import com.rvakva.bus.personal.PersonalService
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 上午10:27
 */
class PersonScheduleViewModel(application: Application) : AndroidViewModel(application) {

    val orderListLiveData by RequestLiveData<EmResult<List<ScheduleDataModel>>>()

    fun getOrderList(
        orderStatusTypeEnum: OrderStatusTypeEnum? = null,
        page: Int
    ) {
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getOrderList(
                    Ktx.getInstance().userDataSource.userId,
                    page,
                    10,
                    orderStatusTypeEnum!!.value
                )
                .requestMap()
        }, requestLiveData = orderListLiveData, showToastBar = false)
    }
}
