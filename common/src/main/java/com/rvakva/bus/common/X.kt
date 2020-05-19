package com.rvakva.bus.common

import com.rvakva.bus.common.util.MyMediaPlayer
import javax.sql.CommonDataSource

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午4:28
 */
class X private constructor() {

    private object SingletonHolder {
        val INSTANCE = X()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    val myMediaPlayer by lazy {
        MyMediaPlayer.getInstance()
    }

//    val commonDataSource by lazy {
//        CommonDataSource.getInstance()
//    }
}