package com.rvakva.bus.home.viewmodel.work

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rvakva.bus.common.CommonService
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.Config
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
 * @CreateDate:     2020/5/14 下午5:33
 */
class WorkViewModel(application: Application) : AndroidViewModel(application){

    val workStatusViewModel by RequestLiveData<BaseResult>()

    private val userRepository = UserRepository()

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
}