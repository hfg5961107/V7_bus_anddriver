package com.rvakva.travel.devkit.datasource

import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:59
 */
class AppKeyDataSource {
    companion object {
        fun getInstance() =
            SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = AppKeyDataSource()
    }

    var appKey = ""
        set(value) {
            field = value
            Ktx.getInstance().mmkv.put(Config.USER_APP_KEY_KEY, value)
        }
        get() {
            if (field.isEmpty()) {
                field =
                    Ktx.getInstance().mmkv.get(Config.USER_APP_KEY_KEY, "")
            }
            return field
        }
}