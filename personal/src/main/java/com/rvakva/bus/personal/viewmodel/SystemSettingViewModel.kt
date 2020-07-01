package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.model.AppInfoModel
import com.rvakva.bus.common.repository.AppUpdateRepository
import com.rvakva.bus.personal.PersonalService
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:35
 */
class SystemSettingViewModel(application: Application) : AndroidViewModel(application) {

    val appInfoLiveData by RequestLiveData<EmResult<AppInfoModel>>()

    val appLogoutLiveData by RequestLiveData<BaseResult>()

    private val appUpdateRepository = AppUpdateRepository()
    fun getAppInfo() {
        launchRequest(block = {
            appUpdateRepository.getAppInfo()
        }, requestLiveData = appInfoLiveData)
    }


    /**
     * 退出app
     */
    fun logout() {
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .logout()
                .requestMap()
        }, requestLiveData = appLogoutLiveData)
    }
}