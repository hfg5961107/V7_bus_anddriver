package com.rvakva.bus.common.repository

import android.util.Log
import com.rvakva.bus.common.CommonService
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.expend.toSha256
import com.rvakva.travel.devkit.retrofit.ApiManager

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:50
 */
class UserRepository {

    suspend fun register(
        phone: String,
        password: String,
        randomString: String,
        smsCode: String
    ) =
        ApiManager.getInstance().createService(CommonService::class.java)
            .register(phone, password.toSha256(), randomString, smsCode)
            .requestMap()
            .let {
                it.data?.let {
                    Ktx.getInstance().userDataSource.userToken = it.token ?: ""
                    Ktx.getInstance().appKeyDataSource.appKey = it.appKey ?: ""
                }
            }

    suspend fun login(phone: String, pswd: String) =
        ApiManager.getInstance().createService(CommonService::class.java)
            .login(phone, pswd.toSha256())
            .requestMap()
            .let {
                it.data?.let {
                    Ktx.getInstance().userDataSource.userToken = it.token ?: ""
                    Ktx.getInstance().appKeyDataSource.appKey = it.appKey ?: ""
                }
            }

    suspend fun getUserConfig() =
        ApiManager.getInstance().createService(CommonService::class.java)
            .getUserConfig()
            .requestMap()
            .apply {
                Ktx.getInstance().userDataSource.insertUserConfig(data)
            }

    suspend fun getUserInfo() =
        ApiManager.getInstance().createService(CommonService::class.java)
            .getUserInfo()
            .requestMap()
            .apply {
                data?.let {
                    Ktx.getInstance().userDataSource.userId = it.id
                    Ktx.getInstance().userDataSource.insertUserInfo(it)
                }
            }

    suspend fun getUserStatistics() =
        ApiManager.getInstance().createService(CommonService::class.java)
            .getUserStatistics()
            .requestMap()
            .apply {
                Ktx.getInstance().userDataSource.userStatisticsLiveData.postValue(data)
            }
}