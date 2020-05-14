package com.rvakva.bus.home.viewmodel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:48
 */
class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val loginLiveData by RequestLiveData<EmResult<UserInfoModel>>()

    private val userRepository = UserRepository()

    fun login(phone: String, pswd: String) {
        launchRequest(
            block = {
                userRepository.login(phone, pswd).let {
//                    userRepository.getUserConfig()
                }.let {
                    userRepository.getUserInfo()
                }
            }, requestLiveData = loginLiveData
        )
    }
}