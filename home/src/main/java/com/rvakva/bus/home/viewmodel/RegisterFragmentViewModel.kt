package com.rvakva.bus.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.expend.getRandomString
import com.rvakva.travel.devkit.expend.launchRepeat
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:31
 */
class RegisterFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val countDownLiveData = MutableLiveData<Int>()
    val sendSmsCodeLiveData by RequestLiveData<BaseResult>()
    val registerLiveData by RequestLiveData<EmResult<UserInfoModel>>()

    private val countDownTime = 60
    private val countDownTimeDelay = 1_000L

    private val userRepository = UserRepository()

    var isCountDownFinish = true

    private val randomString by lazy {
        getRandomString()
    }

    fun sendSmsCode(phone: String, inviteCode: String) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .sendSms(phone, randomString, "DRIVER_REGISTER_CODE", 1,inviteCode)
                    .requestMap()
            },
            requestLiveData = sendSmsCodeLiveData
        )
    }

    fun countDownTimer() {
        isCountDownFinish = false
        var time = countDownTime
        launchRepeat(delayTimeMillis = countDownTimeDelay, times = countDownTime) {
            time--
            countDownLiveData.postValue(time)
        }
    }

    fun register(phone: String, password: String, smsCode: String, inviteCode: String) {
        launchRequest(
            block = {
                userRepository.register(phone, password, randomString, smsCode, inviteCode).let {
                    userRepository.getUserConfig()
                }.let {
                    userRepository.getUserInfo()
                }
            },
            requestLiveData = registerLiveData
        )
    }

}