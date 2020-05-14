package com.rvakva.bus.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.model.AppInfoModel
import com.rvakva.bus.common.repository.AppUpdateRepository
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:51
 */
class IndexActivityViewModel(application: Application) : AndroidViewModel(application) {

    val appInfoLiveData by RequestLiveData<EmResult<AppInfoModel>>()
    private val appUpdateRepository = AppUpdateRepository()

    fun getAppInfo() {
        launchRequest(block = {
            appUpdateRepository.getAppInfo()
        }, requestLiveData = appInfoLiveData)
    }

}