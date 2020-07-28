package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.personal.PersonalService
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.handleSpecialException
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:53
 */
class QrCodeActivityViewModel(application: Application) : AndroidViewModel(application) {


    val getCodeLiveData by RequestLiveData<EmResult<String>>()

    fun getCode(vehicleId:Long) {
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getCode(vehicleId, Ktx.getInstance().appKeyDataSource.appKey)
                .requestMap()
        }, requestLiveData = getCodeLiveData)
    }

}