package com.rvakva.travel.devkit.expend

import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:09
 */
fun resetData() {
    Ktx.getInstance().userDataSource.userToken = ""
    Ktx.getInstance().userDataSource.userId = 0L
}

fun resetApplication() {
    Ktx.getInstance().activityManager.let {
        Config.LOGIN_NAME.let { name ->
            val exist = it.existByName(name)
            it.finishButByName(name)
            if (!exist) {
                resetData()
                jumpByARouter(Config.APP_SPLASH)
            }
        }
    }
}