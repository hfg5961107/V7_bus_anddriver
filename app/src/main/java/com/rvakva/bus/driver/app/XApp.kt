package com.rvakva.bus.driver.app

import androidx.multidex.MultiDexApplication
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.bus.driver.BuildConfig

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 上午11:23
 */
class XApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Config.let {
            it.IMAGE_SERVER = BuildConfig.IMG_SERVER
            it.WECHAT_ID = BuildConfig.WECHAT_ID
            it.BASE_URL = BuildConfig.HOST
            it.VERSION_NAME = BuildConfig.VERSION_NAME
            it.VERSION_CODES = BuildConfig.VERSION_CODE
        }

        Ktx.getInstance().initialize(this)
    }
}