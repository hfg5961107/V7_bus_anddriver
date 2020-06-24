package com.rvakva.bus.home.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.expend.getRandomString
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * @Description:
 * @Author: lch
 * @Date: 2020/6/23 16:03
 **/
class RegisterActivityViewModel (application: Application) : AndroidViewModel(application){

    val sendSmsCodeLiveData by RequestLiveData<BaseResult>()
    val registerLiveData by RequestLiveData<EmResult<UserInfoModel>>()

    private val userRepository = UserRepository()

    fun sendSmsCode(phone: String,mRandom:String) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .sendSms(phone, mRandom, "DRIVER_REGISTER_CODE", 2)
                    .requestMap()
            },
            requestLiveData = sendSmsCodeLiveData
        )
    }

    fun register(phone: String, password: String, smsCode: String, randomString: String) {
        launchRequest(
            block = {
                userRepository.register(phone, password, randomString, smsCode).let {
                    userRepository.getUserConfig()
                }.let {
                    userRepository.getUserInfo()
                }
            },
            requestLiveData = registerLiveData
        )
    }

}