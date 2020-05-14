package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.personal.PersonalService
import com.rvakva.travel.devkit.expend.handleSpecialException
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.retrofit.ApiManager
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
class WalletActivityViewModel(application: Application) : AndroidViewModel(application) {


    val userConfigLiveData by RequestLiveData<EmResult<UserConfigModel>>()

    val userRepository = UserRepository()

    fun cashOut(fee: String) {
        launchRequest(block = {
            handleSpecialException(
                block = {
                    ApiManager.getInstance().createService(PersonalService::class.java)
                        .cashOut(fee)
                        .requestMap()
                        .let {
                            withContext(Dispatchers.Main) {
                                ToastBar.show("成功发起提现申请")
                            }
                        }.let {
                            userRepository.getUserConfig()
                        }
                }
            )
        }, requestLiveData = userConfigLiveData)
    }

    fun getBalance(isFirst: Boolean) {
        launchRequest(block = {
            val method: suspend () -> EmResult<UserConfigModel> = {
                userRepository.getUserConfig()
            }
            if (isFirst) {
                method()
            } else {
                handleSpecialException(block = method)
            }
        }, requestLiveData = userConfigLiveData)
    }

}