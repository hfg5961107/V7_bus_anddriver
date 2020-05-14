package com.rvakva.travel.devkit.datasource

import androidx.lifecycle.MutableLiveData
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.model.UserStatisticsModel
import com.rvakva.travel.devkit.x.XDataBase

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:41
 */
class UserDataSource {

    private object SingletonHolder {
        val INSTANCE = UserDataSource()
    }

    companion object {
        internal fun getInstance() = SingletonHolder.INSTANCE
    }

    suspend fun insertUserInfo(userInfo: UserInfoModel?) {
        userInfo?.let {
            XDataBase.getInstance().userInfoModelDao().insert(it)
        }
    }

    val userInfoLiveData = XDataBase.getInstance().userInfoModelDao().getUserInfo()

    suspend fun insertUserConfig(userConfig: UserConfigModel?) {
        userConfig?.let {
            XDataBase.getInstance().userConfigModelDao().insert(it)
        }
    }

    val userConfigLiveData = XDataBase.getInstance().userConfigModelDao().getUserConfig()

    var userToken = ""
        set(value) {
            field = value
            Ktx.getInstance().mmkv.put(Config.USER_TOKEN_KEY, value)
        }
        get() {
            if (field.isEmpty()) {
                field =
                    Ktx.getInstance().mmkv.get(Config.USER_TOKEN_KEY, "")
            }
            return field
        }

    var userId = 0L
        set(value) {
            field = value
            Ktx.getInstance().mmkv.put(Config.USER_ID_KEY, value)
        }
        get() {
            if (field == 0L) {
                field =
                    Ktx.getInstance().mmkv.get(Config.USER_ID_KEY, 0L)
            }
            return field
        }

    val userStatisticsLiveData = MutableLiveData<UserStatisticsModel>()
}